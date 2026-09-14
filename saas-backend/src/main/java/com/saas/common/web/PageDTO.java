package com.saas.common.web;

import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;

/**
 * 分页请求参数
 *
 * @author saas
 */
@Schema(description = "分页参数")
public class PageDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "当前页码", example = "1")
    private Long current = 1L;

    @Schema(description = "每页条数", example = "10")
    private Long size = 10L;

    @Schema(description = "排序字段")
    private String orderBy;

    @Schema(description = "排序方式: asc/desc")
    private String orderDirection = "desc";

    // Getters and Setters
    public Long getCurrent() { return current; }
    public void setCurrent(Long current) { this.current = current; }
    public Long getSize() { return size; }
    public void setSize(Long size) { this.size = size; }
    public String getOrderBy() { return orderBy; }
    public void setOrderBy(String orderBy) { this.orderBy = orderBy; }
    public String getOrderDirection() { return orderDirection; }
    public void setOrderDirection(String orderDirection) { this.orderDirection = orderDirection; }

    public Long getOffset() {
        return (current - 1) * size;
    }
}
