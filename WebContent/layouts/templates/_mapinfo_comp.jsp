<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<script id="template-mapinfo-comp" type="text/x-tmpl">
<div id="mapinfo_comp_div" class="condition"  style="width:300px; height:200px">
    <h3>修理業者情報</h3>
    <table style="line-height:25px">
        <tr>
            <td width="80px"><label><b>修理業者ID</b>:&nbsp;</label></td>
            <td width="100px">{%=o.comp_id%}</td>
        </tr>
        <tr>
            <td><label><b>修理業者名称</b>:&nbsp;</label></td>
            <td>{%=o.comp_nm%}</td>
        </tr>
        <tr>
            <td><label><b>修理業者住所</b>:&nbsp;</label></td>
            <td>{%=o.comp_addr%}</td>
        </tr>
    </table>
	<input type="button" name="sel_comp_btn" value="選択" onclick="selectComp('{%=o.comp_id%}');"/>
</div>
</script>