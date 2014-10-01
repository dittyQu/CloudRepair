<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<script id="template-mapinfo-cust" type="text/x-tmpl">
<div id="mapinfo_cust_div" class="condition">
    <h3>顧客情報</h3>
    <table style="line-height:25px">
        <tr>
            <td width="80px"><label><b>顧客ID:</b>&nbsp;</label></td>
            <td width="100px">{%=o.cust_id%}</td>
            <td width="10px">&nbsp;</td>
            <td width="60px"><label><b>生年月日</b>:&nbsp;</label></td>
            <td width="300px">{%=o.cust_birth_txt%}</td>
        </tr>
        <tr>
            <td><label><b>名前(漢字)</b>&nbsp;</label></td>
            <td>{%=o.cust_nm%}</td>
            <td>&nbsp;</td>
            <td><label><b>住所</b>:&nbsp;</label></td>
            <td>{%=o.cust_addr%}</td>
        </tr>
        <tr>
            <td><label><b>名前(カタカナ)</b>&nbsp;</label></td>
            <td>{%=o.cust_kn%}</td>
			<td>&nbsp;</td>
            <td><label><b>性別</b>:&nbsp;</label></td>
            <td>{%=o.cust_sex_txt%}</td>
        </tr>
    </table>
</div>
</script>