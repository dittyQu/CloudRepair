<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<script id="template-customer-detail" type="text/x-tmpl">
<div id="cust_sch_div" class="condition">
    <h3>顧客詳細情報</h3>
    <table style="line-height:25px">
        <tr>
            <td width="70px"><label>顧客ID</label></td>
            <td width="100px">{%=o.customer.cust_id%}</td>
            <td width="10px">&nbsp;</td>
            <td width="90px"><label>生年月日</label></td>
            <td width="400px">{%=o.customer.cust_birth_txt%}</td>
        </tr>
        <tr>
            <td><label>名前(漢字)</label></td>
            <td>{%=o.customer.cust_nm%}</td>
            <td>&nbsp;</td>
            <td><label>名前(カタカナ)</label></td>
            <td>{%=o.customer.cust_kn%}</td>
        </tr>
        <tr>
            <td><label>性別</label></td>
            <td>{%=o.customer.cust_sex_txt%}</td>
			<td>&nbsp;</td>
            <td><label>住所</label></td>
            <td>{%=o.customer.cust_addr%}</td>
        </tr>
    </table>
</div>
</script>