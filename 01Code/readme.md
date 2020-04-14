一、Git Commit的日志不能为空¶
二、Git Commit的日志格式规范¶
1、提交规则
	一个commit只做一件事情；若一个commit做了多件事情需要拆分成多个commit；
	严格遵循commit message格式；
	每次只允许提交一个commit，若本地有多个commit等待提交，
	必须等前面的commit合并进入主版本库并在本地合并完成后才可提交后面的commit；
2、提交日志基本格式
	<type>(<scope>): <subject>
	<空行>
	<body>
	<空行>
	<footer>
其中需要注意的是，首行部分是必需的，而 body 和 footer 部分可选。 
	<subject>, <body>, <footer>内容默认使用中文。

	# 标题行：70个字符以内，描述主要变更内容
	#
	# 主体内容：更详细的说明文本，可写可不写，建议72个字符以内。 需要描述的信息包括:
	#
	# * 为什么这个变更是必须的? 它可能是用来修复一个bug，增加一个feature，提升性能、可靠性、稳定性等等
	# * 他如何解决这个问题? 具体描述解决问题的步骤
	# * 是否存在副作用、风险? 
	#
	# 尾部：如果需要的化可以添加一个链接到issue地址或者其它文档，或者关闭某个issue。

3、type取值：
	feat： 新增feature
	fix: 修复bug
	docs: 仅仅修改了文档，比如README, CHANGELOG, CONTRIBUTE等等
	style: 仅仅修改了空格、格式缩进、都好等等，不改变代码逻辑
	refactor: 代码重构，没有加新功能或者修复bug
	perf: 优化相关，比如提升性能、体验
	test: 测试用例，包括单元测试、集成测试等
	chore: 改变构建流程、或者增加依赖库、工具等
	revert: 回滚到上一个版本
	
	