new Vue({
        el : '#app',
        data : function(){
        	return {
        		registerUser:{
            		userName : '',
            		email:'',
            		password : '',
            		confirmPassword : '',
            		rememberMe: false
        		},
        		rules:{
        			userName:[{
        					"required": true,
                        	"message": "用户名不能为空"
        				},
        				{ pattern: /^[A-Za-z0-9]+$/, message: '用户名只允许使用字母和数字' },
        				{min :5,max:20,message:'用户名长度必须介于5-20之间',trigger: 'blur'}
        			],
        			email:[{
        					"required": true,
                        	"message": "邮箱不能为空"
        				},
        				{ pattern: /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/, message: '邮箱格式不正确' }
        			],
        			password:[{
        					"required": true,
                        	"message": "密码不能为空"
        				},
        				{ pattern: /^(?=.*[0-9].*)(?=.*[A-Z].*)(?=.*[a-z].*)(?=.*[!@#$%^&*<>?]).+$/, 
        				message: '密码必须至少包含一个大写字母、一个小写字母、一个数字、一个特殊符号' },
        				{min :8,max:20,message:'密码长度必须介于8-20之间',trigger: 'blur'}
        			],
        			confirmPassword:[{
        					"required": true,
                        	"message": "确认密码不能为空"
        				},
        				{ pattern: /^(?=.*[0-9].*)(?=.*[A-Z].*)(?=.*[a-z].*)(?=.*[!@#$%^&*<>?]).+$/, 
        				message: '密码必须至少包含一个大写字母、一个小写字母、一个数字、一个特殊符号' },
        				{min :8,max:20,message:'密码长度必须介于8-20之间',trigger: 'blur'}
        			]
        		}
        	}
        },
        methods : {
        //注册方法
            register : function(){
            if(this.registerUser.password != this.registerUser.confirmPassword){
            	this.$message({
                        message : '两次输入的密码不一致，请重新输入！',
                        type : 'error'
                    });
                return;
            }
            	var vueObj = this;
                var registerUser = this.registerUser;
                this.$refs.form.validate(function(valid){
                	if(valid){
                		$.ajax({
                    		url : 'user/register',
                    		type : 'post',
                    		dataType : 'json',
                    		contentType : 'application/json',
                    		data : JSON.stringify(registerUser),
                    		success : function(data) {
                    			if(data.success){
                    				vueObj.$message({
                        				message : '注册成功',
                        				type : 'success'
                    				});
                    				location.href = "/registersuccess.html";
                    			}else{
                    				vueObj.$message({
                        				message : '注册失败：' + data.errMessage,
                        				type : 'error'
                    				});
                    			}
                    		},
                    		error : function(data) {
                				vueObj.$message({
                        			message : '服务器异常...',
                        			type : 'error'
                    			});
                    		},
                		})
                	}
                });
                
            }
        }
    })