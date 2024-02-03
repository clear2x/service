package com.datasource.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bean.param.base.BasePageQuery;
import com.bean.vo.base.PageVO;
import com.core.constant.DataBaseConstant;
import com.core.util.CamelCaseUtil;
import org.apache.commons.lang3.StringUtils;

/**
 * @author yuangy
 * @create 2020-07-09 10:53
 */
public class PageUtil {

    /**
     * 转换成Page对象
     *
     * @return Page对象
     */
    public static Page<?> toPage(BasePageQuery query) {
        Page<?> page = new Page<>(query.getPageNum(), query.getPageSize());
        handlePageSort(query, page, DataBaseConstant.CREATE_TIME_FIELD, DataBaseConstant.DESC, Boolean.TRUE);
        return page;
    }

    /**
     * 封装前端分页表格所需数据
     *
     * @param page page
     * @return PageVO
     */
    public static PageVO toPageVO(IPage<?> page) {
        PageVO pageVO = new PageVO();
        pageVO.setRows(page.getRecords());
        pageVO.setTotal(page.getTotal());
        return pageVO;
    }

    /**
     * 处理排序（分页情况下） for mybatis-plus
     *
     * @param query             QueryRequest
     * @param page              Page
     * @param defaultSort       默认排序的字段
     * @param defaultOrder      默认排序规则
     * @param camelToUnderscore 是否开启驼峰转下划线
     */
    public static void handlePageSort(BasePageQuery query, Page<?> page, String defaultSort, String defaultOrder, boolean camelToUnderscore) {
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());
        String sortField = query.getField();
        if (camelToUnderscore) {
            sortField = CamelCaseUtil.camelToUnderscore(sortField);
            defaultSort = CamelCaseUtil.camelToUnderscore(defaultSort);
        }
        if (StringUtils.isNotBlank(query.getField())
                && StringUtils.isNotBlank(query.getOrder())
                && !StringUtils.equalsIgnoreCase(query.getField(), "null")
                && !StringUtils.equalsIgnoreCase(query.getOrder(), "null")) {
            if (StringUtils.equals(query.getOrder(), DataBaseConstant.DESC)) {
                page.addOrder(OrderItem.desc(sortField));
            } else {
                page.addOrder(OrderItem.asc(sortField));
            }
        } else {
            if (StringUtils.isNotBlank(defaultSort)) {
                if (StringUtils.equals(defaultOrder, DataBaseConstant.DESC)) {
                    page.addOrder(OrderItem.desc(defaultSort));
                } else {
                    page.addOrder(OrderItem.asc(defaultSort));
                }
            }
        }
    }

    /**
     * 处理排序 for mybatis-plus
     *
     * @param query QueryRequest
     * @param page  Page
     */
    public static void handlePageSort(BasePageQuery query, Page<?> page) {
        handlePageSort(query, page, null, null, false);
    }

    /**
     * 处理排序 for mybatis-plus
     *
     * @param query             QueryRequest
     * @param page              Page
     * @param camelToUnderscore 是否开启驼峰转下划线
     */
    public static void handlePageSort(BasePageQuery query, Page<?> page, boolean camelToUnderscore) {
        handlePageSort(query, page, null, null, camelToUnderscore);
    }

    /**
     * 处理排序 for mybatis-plus
     *
     * @param query             QueryRequest
     * @param wrapper           wrapper
     * @param defaultSort       默认排序的字段
     * @param defaultOrder      默认排序规则
     * @param camelToUnderscore 是否开启驼峰转下划线
     */
    public static void handleWrapperSort(BasePageQuery query, QueryWrapper<?> wrapper, String defaultSort, String defaultOrder, boolean camelToUnderscore) {
        String sortField = query.getField();
        if (camelToUnderscore) {
            sortField = CamelCaseUtil.camelToUnderscore(sortField);
            defaultSort = CamelCaseUtil.camelToUnderscore(defaultSort);
        }
        if (StringUtils.isNotBlank(query.getField())
                && StringUtils.isNotBlank(query.getOrder())
                && !StringUtils.equalsIgnoreCase(query.getField(), "null")
                && !StringUtils.equalsIgnoreCase(query.getOrder(), "null")) {
            if (StringUtils.equals(query.getOrder(), DataBaseConstant.DESC)) {
                wrapper.orderByDesc(sortField);
            } else {
                wrapper.orderByAsc(sortField);
            }
        } else {
            if (StringUtils.isNotBlank(defaultSort)) {
                if (StringUtils.equals(defaultOrder, DataBaseConstant.DESC)) {
                    wrapper.orderByDesc(defaultSort);
                } else {
                    wrapper.orderByAsc(defaultSort);
                }
            }
        }
    }

    /**
     * 处理排序 for mybatis-plus
     *
     * @param query   QueryRequest
     * @param wrapper wrapper
     */
    public static void handleWrapperSort(BasePageQuery query, QueryWrapper<?> wrapper) {
        handleWrapperSort(query, wrapper, null, null, false);
    }

    /**
     * 处理排序 for mybatis-plus
     *
     * @param query             QueryRequest
     * @param wrapper           wrapper
     * @param camelToUnderscore 是否开启驼峰转下划线
     */
    public static void handleWrapperSort(BasePageQuery query, QueryWrapper<?> wrapper, boolean camelToUnderscore) {
        handleWrapperSort(query, wrapper, null, null, camelToUnderscore);
    }

}
