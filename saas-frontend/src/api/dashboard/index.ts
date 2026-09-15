/**
 * Dashboard API
 * 仪表盘 API
 */

import { http } from '../request';

/** 仪表盘统计数据 */
export interface DashboardStats {
  userCount: number;
  todayLoginCount: number;
  todayActiveCount: number;
  weekLoginTrend: { date: string; count: number }[];
}

/** 获取仪表盘统计 */
export function getDashboardStats(): Promise<DashboardStats> {
  return http.get<DashboardStats>('/dashboard/stats');
}

/** 快捷操作项 */
export interface QuickAction {
  id: string;
  name: string;
  icon: string;
  path: string;
  description?: string;
}

/** 获取快捷操作 */
export function getQuickActions(): Promise<QuickAction[]> {
  return http.get<QuickAction[]>('/dashboard/quick-actions');
}

/** 公告列表项 */
export interface Announcement {
  id: number;
  title: string;
  content: string;
  type: number;
  publishTime: string;
  publisher: string;
}

/** 获取公告列表 */
export function getAnnouncements(): Promise<Announcement[]> {
  return http.get<Announcement[]>('/dashboard/announcements');
}
