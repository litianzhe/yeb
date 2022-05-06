package com.xxxx.server.pojo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * <p>
 *   职位
 * </p>
 *
 * @author ht
 * @since 2022-02-17
 */
@Data
@NoArgsConstructor
@RequiredArgsConstructor // 有参构造,会生成一个包含常量，和标识了NotNull的变量的构造方法。  因为类已经实现了无参构造,上面又写上了@NoArgsConstructor注解,导致了编译失败
@EqualsAndHashCode(callSuper = false ,of = "name") // 以 name 重写 EqualsAndHashCode
@Accessors(chain = true)
@TableName("t_position")
@ApiModel(value="Position对象", description="职位")
public class Position implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "职位")
    @Excel(name = "职位")
    @NonNull // 非空 必填，不填报错
    private String name;

    @ApiModelProperty(value = "创建时间") // 自定义日期格式
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "Asia/Shanghai")
    private LocalDateTime createDate;

    @ApiModelProperty(value = "是否启用")
    private Boolean enabled;


}
