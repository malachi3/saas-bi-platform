/**
 * Format Utilities
 * 格式化工具函数
 */

import dayjs from 'dayjs';

/** 日期格式 */
export type DateFormat = 'date' | 'datetime' | 'time' | 'timestamp';

/**
 * 格式化日期
 */
export function formatDate(
  date: string | number | Date | undefined | null,
  format: DateFormat = 'datetime'
): string {
  if (!date) return '-';

  const d = dayjs(date);

  if (!d.isValid()) {
    return '-';
  }

  const formats: Record<DateFormat, string> = {
    date: 'YYYY-MM-DD',
    datetime: 'YYYY-MM-DD HH:mm:ss',
    time: 'HH:mm:ss',
    timestamp: 'X',
  };

  return d.format(formats[format]);
}

/**
 * 相对时间格式化
 */
export function formatRelativeTime(date: string | number | Date | undefined | null): string {
  if (!date) return '-';

  const d = dayjs(date);

  if (!d.isValid()) {
    return '-';
  }

  const now = dayjs();
  const diff = now.diff(d, 'minute');

  if (diff < 1) {
    return '刚刚';
  }
  if (diff < 60) {
    return `${diff}分钟前`;
  }
  if (diff < 1440) {
    return `${Math.floor(diff / 60)}小时前`;
  }
  if (diff < 10080) {
    return `${Math.floor(diff / 1440)}天前`;
  }

  return d.format('YYYY-MM-DD');
}

/**
 * 格式化数字
 */
export function formatNumber(num: number | undefined | null, decimals = 0): string {
  if (num === undefined || num === null) return '-';

  return num.toLocaleString('zh-CN', {
    minimumFractionDigits: decimals,
    maximumFractionDigits: decimals,
  });
}

/**
 * 格式化文件大小
 */
export function formatFileSize(bytes: number | undefined | null): string {
  if (bytes === undefined || bytes === null) return '-';

  if (bytes === 0) return '0 B';

  const units = ['B', 'KB', 'MB', 'GB', 'TB'];
  const k = 1024;
  const i = Math.floor(Math.log(bytes) / Math.log(k));

  return `${(bytes / Math.pow(k, i)).toFixed(2)} ${units[i]}`;
}

/**
 * 格式化金额
 */
export function formatCurrency(
  amount: number | undefined | null,
  currency = 'CNY',
  decimals = 2
): string {
  if (amount === undefined || amount === null) return '-';

  return new Intl.NumberFormat('zh-CN', {
    style: 'currency',
    currency,
    minimumFractionDigits: decimals,
    maximumFractionDigits: decimals,
  }).format(amount);
}

/**
 * 脱敏手机号
 */
export function maskPhone(phone: string | undefined | null): string {
  if (!phone) return '-';

  if (phone.length === 11) {
    return phone.replace(/(\d{3})\d{4}(\d{4})/, '$1****$2');
  }

  return phone;
}

/**
 * 脱敏邮箱
 */
export function maskEmail(email: string | undefined | null): string {
  if (!email) return '-';

  const [username, domain] = email.split('@');

  if (!domain) return '-';

  const maskedUsername = username.length > 2
    ? username.slice(0, 2) + '***'
    : username + '***';

  return `${maskedUsername}@${domain}`;
}

/**
 * 截断文本
 */
export function truncate(text: string | undefined | null, maxLength = 50): string {
  if (!text) return '-';

  if (text.length <= maxLength) {
    return text;
  }

  return text.slice(0, maxLength) + '...';
}

const format = {
  date: formatDate,
  relativeTime: formatRelativeTime,
  number: formatNumber,
  fileSize: formatFileSize,
  currency: formatCurrency,
  maskPhone,
  maskEmail,
  truncate,
};

export default format;
