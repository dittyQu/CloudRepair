<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<script id="template-prod-detail" type="text/x-tmpl">
<div id="cust_sch_div" class="condition">
    <h3>家電詳細情報</h3>
    <table style="line-height:25px">
        <tr>
            <td width="80px"><label>シリアルID</label></td>
            <td width="160px">{%=o.product.ser_no%}</td>
            <td width="10px">&nbsp;</td>
            <td width="60px">&nbsp;</td>
            <td width="300px">&nbsp;</td>
        </tr>
        <tr>
			<td><label>製品カテゴリ</label></td>
            <td>{%=o.product.product.prod_cat%}</td>
			<td>&nbsp;</td>
            <td><label>製品名</label></td>
            <td>{%=o.product.product.prod_nm%}</td>
        </tr>
        <tr>
            <td><label>購入日</label></td>
            <td>{%=o.product.sal_date_txt%}</td>
			<td>&nbsp;</td>
			<td width="60px"><label>保障期間</label></td>
            <td width="300px">{%=o.product.product.ensure_period%}(月)</td>
        </tr>
    </table>
</div>
</script>