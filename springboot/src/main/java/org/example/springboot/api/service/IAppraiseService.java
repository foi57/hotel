package org.example.springboot.api.service;

import org.example.springboot.api.entity.Appraise;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author baomidou
 * @since 2025-02-06
 */
public interface IAppraiseService extends IService<Appraise> {
    int insertAppraise(Appraise appraise);
}
