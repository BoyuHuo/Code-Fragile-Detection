function ajaxMessageReader(response, func) {
    if (response.code == "200") {
        func(response.data);
    } else {
        alert(response.get("message"));
    }

}

function uploadTag() {
    var project_name = $("#project-name");
    var git_url = $("#git-url");
    var branch = $("#branch");
    var code_url = $("#code-url");
    var data = {}

    var trArray = $("#result-body").find('tr').each(function() {
        var tdArr = $(this).children();
        var filePath = tdArr.eq(1).html();
        var tag = tdArr.eq(4).find('input').attr('checked') ? '1' : 0;
        data[filePath] = tag;
    });

    $.ajax({
        url: "/rest/vul/upload",
        type: "POST",
        contentType: 'application/json',
        dataType: "json",
        data: JSON.stringify({
            project_name: project_name.val(),
            git_url: git_url.val(),
            branch: branch.val(),
            code_url: code_url.val(),
            data: data
        }),
        beforeSend: function(XMLHttpRequest) {
            $("#Loading").show();
        },
        timeout: 0,
        success: function(data) {
            ajaxMessageReader(data, function(data) {
                alert("Tag Upload Successful, thank you")
            })
        },
        error: function(data) {
            alert(data.error);
        },
        complete: function(XMLHttpRequest, textStatus) {
            $("#Loading").hide();
        }
    });
}

function codeDetect() {
    var project_name = $("#project-name");
    var git_url = $("#git-url");
    var branch = $("#branch");
    var code_url = $("#code-url");

    $.ajax({
        url: "/rest/vul",
        type: "POST",
        contentType: 'application/json',
        dataType: "json",
        data: JSON.stringify({
            project_name: project_name.val(),
            git_url: git_url.val(),
            branch: branch.val(),
            code_url: code_url.val()
        }),
        beforeSend: function(XMLHttpRequest) {
            $("#Loading").show();
        },
        timeout: 0,
        success: function(data) {
            ajaxMessageReader(data, function(data) {
                var resultBody = $("#result-body");
                resultBody.empty();

                $.each(data["data"], function(key, value) {
                    let tr = "<tr>";
                    let keyStr = key.split("/");
                    let fileName = keyStr[keyStr.length - 1];
                    let tdFileName = "<td> " + fileName + "</td>";
                    let tdFilePath = "<td>" + key + "</td>";
                    let tdValue = "<td>" + value + "</td>"
                    let tdRisk = "<td style='color:#67c23a'>NO</td>";
                    let value_float = parseFloat(value);
                    if (value_float == 0) {
                        tdRisk = "<td style='color:#67c23a'>NO</td>";
                    } else {
                        tdRisk = "<td style='color:#dd6161'>YES</td>";
                    }
                    let tdTag = "<input type='checkbox' id='" + key + "' name='tag' value='0'> <label for='vehicle1'> Risky</label><br>";

                    tr += tdFileName;
                    tr += tdFilePath;
                    tr += tdRisk;
                    tr += tdValue;
                    tr += tdTag;
                    tr += "</tr>";

                    resultBody.append(tr);

                    $('#uploadBtn').show();
                });
            })
        },
        error: function(data) {
            alert(data.error);
        },
        complete: function(XMLHttpRequest, textStatus) {
            $("#Loading").hide();
        }
    });
}