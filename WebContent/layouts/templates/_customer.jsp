<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<script id="template-customer" type="text/x-tmpl">
    {% for (var i=0, customer; customer=o.customers[i]; i++) { %}
    <tr>
		<td>{%=customer.cust_id%}</td>
		<td>{%=customer.cust_nm%}</td>
		<td>{%=customer.cust_kn%}</td>
		<td>{%=customer.cust_sex_txt%}</td>
		<td>{%=customer.cust_birth_txt%}</td>
		<td>{%=customer.cust_addr%}</td>
		<td><input type="button" name="cust_detail_btn" value="表示" data-id="{%=customer.cust_id%}"/></td>
    </tr>
    {% } %}
</script>