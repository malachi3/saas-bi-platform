/**
 * Storage Utilities
 * 存储工具函数
 */

/** 存储类型 */
type StorageType = 'local' | 'session';

/** Storage 配置 */
interface StorageConfig {
  prefix?: string;
  storage?: StorageType;
}

/** 默认前缀 */
const DEFAULT_PREFIX = 'saas_';

/**
 * 获取存储实例
 */
function getStorage(type: StorageType): Storage {
  return type === 'local' ? localStorage : sessionStorage;
}

/**
 * 获取带前缀的键名
 */
function getKey(key: string, prefix: string): string {
  return `${prefix}${key}`;
}

/**
 * 存储数据
 */
export function setItem<T>(key: string, value: T, config: StorageConfig = {}): void {
  const { prefix = DEFAULT_PREFIX, storage = 'local' } = config;
  const storageInstance = getStorage(storage);
  const serialized = JSON.stringify(value);
  storageInstance.setItem(getKey(key, prefix), serialized);
}

/**
 * 获取数据
 */
export function getItem<T>(key: string, config: StorageConfig = {}): T | null {
  const { prefix = DEFAULT_PREFIX, storage = 'local' } = config;
  const storageInstance = getStorage(storage);
  const item = storageInstance.getItem(getKey(key, prefix));

  if (item === null) {
    return null;
  }

  try {
    return JSON.parse(item) as T;
  } catch {
    return item as unknown as T;
  }
}

/**
 * 删除数据
 */
export function removeItem(key: string, config: StorageConfig = {}): void {
  const { prefix = DEFAULT_PREFIX, storage = 'local' } = config;
  const storageInstance = getStorage(storage);
  storageInstance.removeItem(getKey(key, prefix));
}

/**
 * 清空存储
 */
export function clear(config: StorageConfig = {}): void {
  const { storage = 'local' } = config;
  const storageInstance = getStorage(storage);
  storageInstance.clear();
}

/**
 * 检查键是否存在
 */
export function hasItem(key: string, config: StorageConfig = {}): boolean {
  const { prefix = DEFAULT_PREFIX, storage = 'local' } = config;
  const storageInstance = getStorage(storage);
  return storageInstance.getItem(getKey(key, prefix)) !== null;
}

/**
 * 获取所有键
 */
export function keys(config: StorageConfig = {}): string[] {
  const { prefix = DEFAULT_PREFIX, storage = 'local' } = config;
  const storageInstance = getStorage(storage);
  const result: string[] = [];

  for (let i = 0; i < storageInstance.length; i++) {
    const key = storageInstance.key(i);
    if (key?.startsWith(prefix)) {
      result.push(key.slice(prefix.length));
    }
  }

  return result;
}

const storage = {
  set: setItem,
  get: getItem,
  remove: removeItem,
  clear,
  has: hasItem,
  keys,
};

export default storage;
