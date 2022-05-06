<template>
    <div>
        <el-card class="box-card" style="width: 400px;">
            <div slot="header" class="clearfix">
                <span>{{admin.name}}</span>
            </div>
            <div>   
                <div style="display: flex;justify-content: center;">  
                    <!-- FDS 没有安装 -->
                    <el-upload
                            :headers="headers"
                            :data="admin"
                            :on-success="onSuccess"
                            :show-file-list="false"
                            action="/admin/userFace">
                        <img style="width: 100px;height: 100px;border-radius: 50px"
                             title="点击修改用户头像"
                             :src="admin.userFace"
                             alt="">
                    </el-upload>
                </div>
                <div>电话号码：<el-tag>{{ admin.telephone }}</el-tag></div>
                <div>手机号码：<el-tag>{{ admin.phone }}</el-tag></div>
                <div>居住地址：<el-tag>{{ admin.address }}</el-tag></div>
                <div>用户标签：<el-tag type="success"  v-for="(r,index) in admin.roles" :key="index">
                    {{r.nameZh }}</el-tag>
                </div>
                <div style="display: flex;justify-content: space-between;margin-top: 10px">
                    <!-- 1-3  @click="showUpdateAdminInfo" -->
                    <el-button size="mini" type="primary" @click="showUpdateAdminInfo">修改信息</el-button>
                    <!-- 2-1 用户修改密码 @click="showUpdateAdminPassWord"  -->
                    <el-button size="mini" type="danger"  @click="showUpdateAdminPassWord" >修改密码</el-button>
                </div>
                
                
            </div>
        </el-card>
       <!-- 1-1 编辑用户信息 -->
       <el-dialog
                title="编辑用户信息"
                :visible.sync="dialogVisible"
                width="30%">
            <div>
                <el-form ref="form" :model="admin2" label-width="100px">
                    <el-form-item label="用户昵称">
                        <el-input v-model="admin2.name"></el-input>
                    </el-form-item>
                    <el-form-item label="电话号码">
                        <el-input v-model="admin2.telephone"></el-input>
                    </el-form-item>
                    <el-form-item label="手机号码">
                        <el-input v-model="admin2.phone"></el-input>
                    </el-form-item>
                    <el-form-item label="居住地址">
                        <el-input v-model="admin2.address"></el-input>
                    </el-form-item>
                </el-form>
            </div>
            <span slot="footer" class="dialog-footer">
                <el-button @click="dialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="updateAdminInfo">确 定</el-button>
            </span>
        </el-dialog>
        <!-- 2-2 修改密码 -->
        <el-dialog
        title="更新密码"
        :visible.sync="passwordDialogVisible"
        width="30%">
      <div>
        <!-- 2-8 调整修改密码表单 -->
        <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
          <el-form-item label="请输入旧密码" prop="oldPass">
            <el-input type="password" v-model="ruleForm.oldPass" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="请输入新密码" prop="pass">
            <el-input type="password" v-model="ruleForm.pass" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="确认新密码" prop="checkPass">
            <el-input type="password" v-model="ruleForm.checkPass" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submitForm('ruleForm')">提交</el-button>
            <el-button @click="resetForm('ruleForm')">重置</el-button>
          </el-form-item>
        </el-form>
      </div>
    </el-dialog>
    </div>
</template>

<script>
    export default{
        name:"AdminInfo",
        data(){
            // 2-5 修改密码校验规则 一定要放最前面
            var validatePass = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请输入密码'));
                } else {
                    if (this.ruleForm.checkPass !== '') {
                    this.$refs.ruleForm.validateField('checkPass');
                    }
                    callback();
                }
            }
            var validatePass2 = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请再次输入密码'));
                } else if (value !== this.ruleForm.pass) {
                    callback(new Error('两次输入密码不一致!'));
                } else {
                    callback();
                }
            };
            return {
                headers: {
                    Authorization : window.sessionStorage.getItem('tokenStr')
                },
                admin:[],
                admin2:[],   // 1-5 编辑的对象
                dialogVisible: false,  // 1-2 编辑用户信息
                passwordDialogVisible: false, // 2-3 修改密码
                ruleForm: {
                    pass: '',
                    checkPass: '',
                    oldPass: ''// 2-9
                },
                // 2-6 校验对象 规则
                rules: {
                    pass: [
                        { validator: validatePass, trigger: 'blur' }
                    ],
                    checkPass: [
                        { validator: validatePass2, trigger: 'blur' }
                    ],
                    oldPass: [
                        { validator: validatePass, trigger: 'blur' }
                    ]
                }
            }
        },
        mounted() {
            this.initAdmin();
        },
        methods: {
            //  修改用户图像
            onSuccess(){
                this.initAdmin()
            },
            // 2-7 预校验 提交表单
            submitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        // alert('submit!');
                        this.ruleForm.adminId = this.admin.id
                        this.putRequest('/admin/pass',this.ruleForm).then(res => {
                            if (res){
                                // 更新密码成功后  退出登录
                                this.postRequest('/logout')  // 退出登录
                                window.sessionStorage.removeItem('user')
                                window.sessionStorage.removeItem('tokenStr')
                                this.$store.commit('initRoutes',[])   // 初始化路由 菜单 置空
                                this.$router.replace('/')   // 跳到登录页面
                            }
                        })
                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                });
            },
            // 1-9 更新用户
            updateAdminInfo(){
                this.putRequest('/admin/info', this.admin2).then(resp => {
                    if (resp) {
                    this.dialogVisible = false
                    this.initAdmin()
                    }
                })
            },
            // 2-7 重围修改密码表单
            resetForm(formName) {
                this.$refs[formName].resetFields();
            },
            // 2-4 修改密码
            showUpdateAdminPassWord(){
                this.passwordDialogVisible = true
            },
            // 1-4 编辑用户信息弹框
            showUpdateAdminInfo(){
                this.dialogVisible = true
            },
            initAdmin() {
                this.getRequest('/admin/info').then(resp => {
                    if (resp) {
                    this.admin = resp;
                    this.admin2 = Object.assign({}, this.admin) // 1-6 对象拷贝给 admin2
                    // 存入并更新信息
                    window.sessionStorage.setItem('user', JSON.stringify(resp))
                    this.$store.commit('INIT_ADMIN', resp)
                    }
                })
            }
        }
    }
</script>

<style scoped>

</style>