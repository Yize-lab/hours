<html>
<h3 align="left">工时填报告警统计</h3>
<table border=1 align="left">
    <thead>
    <tr bgcolor="#b0c4de">
        <td width="50">序号</td>
        <td width="300">填写周期</td>
        <td width="200">部门</td>
        <td width="200">未完成率</td>
    </thead>

    <tbody>
    <#list warnModelList as warnModel>
        <tr>
            <td>${warnModel.number}</td>
            <td>${warnModel.period}</td>
            <td>${warnModel.department}</td>
            <td>${warnModel.proportion}</td>
        </tr>
    </#list>
    </tbody>
</table>
</html>