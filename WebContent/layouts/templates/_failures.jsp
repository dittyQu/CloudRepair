<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<script id="template-failures" type="text/x-tmpl">
    {% for (var i=0, failure; failure=o.failures[i]; i++) { %}
    <tr>
		<td>{%=failure.repr_dt_txt%}</td>
		<td>{%=failure.status%}</td>
		<td>{%=failure.company.comp_nm%}</td>
		<td>{%=failure.worker.worker_nm%}</td>
		<td>{%=failure.repair_cost_txt%}（円）</td>
		<td><input type="button" name="evidence_btn" value="エビデンス" data-id="{%=failure.failure_id%}"/></td>
    </tr>
    {% } %}
</script>