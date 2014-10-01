<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<script id="template-products" type="text/x-tmpl">
    {% for (var i=0, prod_info; prod_info=o.products[i]; i++) { %}
    <tr>
		<td {%= prod_info.exp_flg ? "class=expired": ""  %}>{%=prod_info.product.prod_cat%}</td>
		<td {%= prod_info.exp_flg ? "class=expired": ""  %}>{%=prod_info.ser_no%}</td>
		<td {%= prod_info.exp_flg ? "class=expired": ""  %}>{%=prod_info.product.prod_nm%}</td>
		<td {%= prod_info.exp_flg ? "class=expired": ""  %}>{%=prod_info.sal_date_txt%}</td>
		<td {%= prod_info.exp_flg ? "class=expired": ""  %}>{%=prod_info.product.ensure_period%}（月）</td>
		<td {%= prod_info.exp_flg ? "class=expired": ""  %}><input type="button" name="prod_detail_btn" value="表示" data-cust_id="{%=prod_info.cust_id%}" data-prod_id="{%=prod_info.product.prod_id%}" data-ser_no="{%=prod_info.ser_no%}"/></td>
    </tr>
    {% } %}
</script>