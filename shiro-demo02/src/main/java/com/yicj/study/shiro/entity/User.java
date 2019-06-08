package com.yicj.study.shiro.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
/**
 * <p>
 * 
 * </p>
 *
 * @author yicj
 * @since 2019-06-07
 */
@Data
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String name;
    private String password;
    private Integer state ;//等于状态，0:正常，1:被锁
    @TableField(exist = false)
    private List<Role> roles;
}
