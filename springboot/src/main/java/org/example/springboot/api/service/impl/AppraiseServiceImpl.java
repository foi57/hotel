package org.example.springboot.api.service.impl;

import org.example.springboot.api.entity.Appraise;
import org.example.springboot.api.mapper.AppraiseMapper;
import org.example.springboot.api.service.IAppraiseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author baomidou
 * @since 2025-02-06
 */
@Service
public class AppraiseServiceImpl extends ServiceImpl<AppraiseMapper, Appraise> implements IAppraiseService {
    AppraiseMapper appraiseMapper;

    @Autowired
    public AppraiseServiceImpl(AppraiseMapper appraiseMapper) {
        this.appraiseMapper = appraiseMapper;
    }

    @Override
    public int insertAppraise(Appraise appraise) {
        return appraiseMapper.insert(appraise);
    }
}
