<html>
<head></head>

<body>
<table>
    <th>File Name</th><th>File Owner</th><th>File Upload Date</th>
    <#list files as file>
        <tr>
            <td>${file.fileName}</td><td>${file.owner}</td><td>${file.uploadDate}</td>
        </tr>
    </#list>
</table>
</body>

</html>