/**
 * Validate Utilities
 * 校验工具函数
 */

/**
 * 校验规则
 */
export interface ValidationRule {
  validator?: (value: unknown) => boolean;
  message: string;
}

/**
 * 校验结果
 */
export interface ValidationResult {
  valid: boolean;
  message?: string;
}

/**
 * 校验手机号
 */
export function validatePhone(phone: string | undefined | null): ValidationResult {
  if (!phone) {
    return { valid: false, message: '请输入手机号' };
  }

  const phoneRegex = /^1[3-9]\d{9}$/;

  if (!phoneRegex.test(phone)) {
    return { valid: false, message: '手机号格式不正确' };
  }

  return { valid: true };
}

/**
 * 校验邮箱
 */
export function validateEmail(email: string | undefined | null): ValidationResult {
  if (!email) {
    return { valid: false, message: '请输入邮箱' };
  }

  const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

  if (!emailRegex.test(email)) {
    return { valid: false, message: '邮箱格式不正确' };
  }

  return { valid: true };
}

/**
 * 校验密码强度
 */
export function validatePassword(password: string | undefined | null): ValidationResult {
  if (!password) {
    return { valid: false, message: '请输入密码' };
  }

  if (password.length < 8) {
    return { valid: false, message: '密码长度不能少于8位' };
  }

  if (password.length > 32) {
    return { valid: false, message: '密码长度不能超过32位' };
  }

  // 包含数字
  if (!/\d/.test(password)) {
    return { valid: false, message: '密码必须包含数字' };
  }

  // 包含字母
  if (!/[a-zA-Z]/.test(password)) {
    return { valid: false, message: '密码必须包含字母' };
  }

  return { valid: true };
}

/**
 * 校验密码确认
 */
export function validateConfirmPassword(
  password: string,
  confirmPassword: string | undefined | null
): ValidationResult {
  if (!confirmPassword) {
    return { valid: false, message: '请确认密码' };
  }

  if (password !== confirmPassword) {
    return { valid: false, message: '两次输入的密码不一致' };
  }

  return { valid: true };
}

/**
 * 校验用户名
 */
export function validateUsername(username: string | undefined | null): ValidationResult {
  if (!username) {
    return { valid: false, message: '请输入用户名' };
  }

  if (username.length < 3) {
    return { valid: false, message: '用户名长度不能少于3位' };
  }

  if (username.length > 20) {
    return { valid: false, message: '用户名长度不能超过20位' };
  }

  const usernameRegex = /^[a-zA-Z0-9_]+$/;

  if (!usernameRegex.test(username)) {
    return { valid: false, message: '用户名只能包含字母、数字和下划线' };
  }

  return { valid: true };
}

/**
 * 校验必填
 */
export function validateRequired(value: unknown, fieldName = '该字段'): ValidationResult {
  if (value === undefined || value === null || value === '') {
    return { valid: false, message: `${fieldName}不能为空` };
  }

  if (Array.isArray(value) && value.length === 0) {
    return { valid: false, message: `${fieldName}不能为空` };
  }

  return { valid: true };
}

/**
 * 校验 URL
 */
export function validateUrl(url: string | undefined | null): ValidationResult {
  if (!url) {
    return { valid: false, message: '请输入网址' };
  }

  try {
    new URL(url);
    return { valid: true };
  } catch {
    return { valid: false, message: '网址格式不正确' };
  }
}

/**
 * 校验 ID Card
 */
export function validateIdCard(idCard: string | undefined | null): ValidationResult {
  if (!idCard) {
    return { valid: false, message: '请输入身份证号' };
  }

  const idCardRegex = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;

  if (!idCardRegex.test(idCard)) {
    return { valid: false, message: '身份证号格式不正确' };
  }

  return { valid: true };
}

/**
 * 校验数字范围
 */
export function validateRange(
  value: number | undefined | null,
  min: number,
  max: number
): ValidationResult {
  if (value === undefined || value === null) {
    return { valid: false, message: '请输入数值' };
  }

  if (value < min || value > max) {
    return { valid: false, message: `数值必须在${min}到${max}之间` };
  }

  return { valid: true };
}

/**
 * 通用校验器
 */
export function validate(value: unknown, rules: ValidationRule[]): ValidationResult {
  for (const rule of rules) {
    if (rule.validator && !rule.validator(value)) {
      return { valid: false, message: rule.message };
    }
  }

  return { valid: true };
}

const validator = {
  phone: validatePhone,
  email: validateEmail,
  password: validatePassword,
  confirmPassword: validateConfirmPassword,
  username: validateUsername,
  required: validateRequired,
  url: validateUrl,
  idCard: validateIdCard,
  range: validateRange,
  validate,
};

export default validator;
