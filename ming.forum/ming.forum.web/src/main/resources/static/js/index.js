new Vue({
        el: '#messageboard',
        data: function() {
            return {
                message: '',
                loginStatus: false,
                commentList:[],
                loginUser:{
                	id : 0,
            		userName : '',
            		password : '',
            		rememberMe: false
            	},
            }
        },
        methods: {
            submitMessage(commentItem){
            	var newMsg = null;
            	if(commentItem == null){
            		//顶级留言
            		newMsg = this.message;
            	}else{
            		//对顶级留言的评论
            		newMsg = commentItem.addedComment;
            	}
      			if(newMsg=='' || newMsg.replace(/(^\s*)|(\s*$)/g, "")==""){
          			this.$message({message:'请输入留言！',type:'warning'});
          			return;
      			}else{
      				if(newMsg.length<3){
      					this.$message({message:'请至少输入3个字！',type:'warning'});
          				return;
      				}
      				var vueObj = this;
      				var loginUser = this.loginUser;
      				var content = newMsg;
      				var commentList = this.commentList;
      				var loginStatus = this.loginStatus;
      				var newComment = null;
      				//传入的参数为null时，说明是发表顶级留言，不为null时说明是给顶级留言评论
      				if(commentItem == null){
      					newComment = {
                        	"userId" : loginUser.id, //留言所属用户id
                        	"userName" : loginUser.userName, //留言所属用户名
                        	"loginUserId" : loginUser.id, //当前登录用户id，用户发表子级评论时使用
                        	"loginUserName": loginUser.userName, //当前登录用户名
                        	"parentUserName": '',
                        	"content" : content,
                        	"loginStatus" :loginStatus,
                        	"addedComment" :'',
                        	"createTime" : '',
                        	"commentChildren" : []
                    	};
      				}else{
      					newComment = {
                        	"userId" : loginUser.id,
                        	"userName" : loginUser.userName,
                        	"loginUserId" : loginUser.id,
                        	"loginUserName": loginUser.userName,
                        	"parentId" : commentItem.id,
                        	"parentUserName": commentItem.userName,
                        	"content" : content,
                        	"loginStatus" :commentItem.loginStatus,
                        	"addedComment" :'',
                        	"createTime" : '',
                        	"commentChildren" : []
                    	};
      				}
      				//提交留言
      				$.ajax({
                    	url : 'comment/submit',
                    	type : 'post',
                    	contentType : 'application/json',
                    	dataType : 'json',
                    	data : JSON.stringify(newComment),
                    	success : function(result) {
                    		if(result.success){
                    			console.log(result.data);
                    			if(commentItem == null){
                    				commentList.unshift(result.data);
                    			}else{
                    				commentItem.commentChildren.unshift(result.data);
                    			}
                    		}else{
                    			vueObj.$message({message:result.errMessage,type:'error'});
                    		}
                    	},
                    	error : function(data) {
                        	vueObj.$message({message:'服务器异常，请刷新后重试！',type:'error'});
                    	},
                	})
      			}      
      		},
      		//是否显示评论输入框
      		showEditComment(comment){
      			comment.showEditComment = !comment.showEditComment;
      		},
      		//退出登录
      		logout(){
      			$.ajax({
                    		url : 'user/logout',
                    		type : 'post',
                    		dataType : 'json',
                    		data : {},
                    		success : function(result) {
                    			if(result.success){
                    				//退出成功
                    				location.href = "/index.html";
                    			}
                    		},
                    		error : function(data) {
                        		location.href="/error/500.html";
                    		},
                		});
      		},
      		//获取留言列表
      		getCommentList(){
      			var vueObj = this;
      			$.ajax({
                    		url : 'comment/list',
                    		type : 'get',
                    		dataType : 'json',
                    		data : {},
                    		success : function(result) {
                    			if(result.success){
                    				vueObj.commentList = result.data;
                    				console.log(vueObj.commentList);
                    			}else{
                    				vueObj.$message({message:'加载留言失败，请刷新后重试！',type:'error'});
                    			}
                    		},
                    		error : function(data) {
                        		vueObj.$message({message:'服务器异常，请刷新后重试！',type:'error'});
                    		},
                });
      		}
        },
        //树组件
        components:{
            mingcommentcom:{
                name:"mingcommentcom",
                template:'#commenttree',
                props:['parmsg','index'],
                methods:{
                	showEditComment(comment){
      					comment.showEditComment = !comment.showEditComment;
      				},
      				submitMessage(commentItem){
      					var vueObj = this;
      					if(commentItem.addedComment=='' || commentItem.addedComment.replace(/(^\s*)|(\s*$)/g, "")==""){
          					vueObj.$message({message:'请输入留言！',type:'warning'});
          					return;
      					}else{
      						if(commentItem.addedComment.length<3){
      							vueObj.$message({message:'请至少输入3个字！',type:'warning'});
          						return;
      						}
      						console.log(commentItem.loginUserName);
      						//构建留言
      						var newComment = {
                        		"userId" : commentItem.loginUserId,
                        		"userName" : commentItem.loginUserName,
                        		"parentId" : commentItem.id,
                        		"parentUserName": commentItem.userName,
                        		"content" : commentItem.addedComment,
                        		"loginStatus" :commentItem.loginStatus,
                        		"addedComment" :'',
                        		"createTime" : '',
                        		"commentChildren" : []
                    		};
                    		//提交留言
      						$.ajax({
                    			url : 'comment/submit',
                    			type : 'post',
                    			contentType : 'application/json',
                    			dataType : 'json',
                    			data : JSON.stringify(newComment),
                    			success : function(result) {
                    				if(result.success){
                    					console.log(result.data);
                    					commentItem.commentChildren.unshift(result.data);
                    				}else{
                    					vueObj.$message({message:result.errMessage,type:'error'});
                    				}
                    			},
                    			error : function(data) {
                        			vueObj.$message({message:'服务器异常，请刷新后重试！',type:'error'});
                    			},
                			})
      					}      
      				}
                }
			}
		},
		created:function(){
			var vueObj = this;
			$.ajax({
                    		url : 'user/checkLogin',
                    		type : 'post',
                    		dataType : 'json',
                    		data : {},
                    		success : function(result) {
                    			if(!result.success){
                    				vueObj.loginUser.userName = "你好";
                    				vueObj.loginUser.email = "游客";
                    				vueObj.loginStatus = false;
                    				return;
                    			}
                    			vueObj.loginUser = result.data;
                    			vueObj.loginStatus = result.success;
                    		},
                    		error : function(data) {
                        		location.href="/error/500.html";
                    		},
           });
           //加载留言列表
           this.getCommentList();
		}
    })