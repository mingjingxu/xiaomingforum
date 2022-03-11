new Vue({
        el : '#app',
        data : function(){
            return {
            	loginUser:{
            		userName : '',
            		password : '',
            		rememberMe: false
            	},
        		rules:{
        			userName:[{
        					"required": true,
                        	"message": "用户名不能为空"
        				}
        			],
        			password:[{
        					"required": true,
                        	"message": "密码不能为空"
        				}
        			]
        		}
            }
        },
        methods : {
        //登录方法
            login : function(){
            	var vueObj = this;
            	var loginUser = this.loginUser;
            	this.$refs.form.validate(function(valid){
            		if(valid){
            			$.ajax({
                    		url : 'user/login',
                    		type : 'post',
                    		dataType : 'json',
                    		data : {"loginCode":loginUser.userName, "password": loginUser.password, "rememberMe": loginUser.rememberMe},
                    		success : function(result) {
                    			if(result.success){
                    				vueObj.$message({
                        				message : '登录成功',
                        				type : 'success'
                    				});
                    				location.href = "/index.html";
                    			}else{
                    				vueObj.$message({
                        				message : result.errMessage,
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
                		});
            		}
            	});
            }
        },
        created: function () {
        //获取登录状态
            $.ajax({
                    		url : 'user/checkLogin',
                    		type : 'post',
                    		dataType : 'json',
                    		data : {},
                    		success : function(result) {
                    			if(result.success){
                    				location.href = "/index.html";
                    			}
                    		},
                    		error : function(data) {
                        		location.href="/error/500.html";
                    		},
                		});
        }
    })