function ajaxMessageReader(response, func) {
    if (response.code == "200") {
        func(response.data);
    } else {
        alert(response.get("message"));
    }

}

function codeDetect() {
    var project_name = $("#project-name");
    var git_url = $("#git-url");
    var branch = $("#branch");
    var code_url = $("#code-url");

    $.ajax({
        url: "/rest/vul",
        data: JSON.stringify({
            project_name: project_name.val(),
            git_url: git_url.val(),
            branch: branch.val(),
            code_url: code_url.val()
        }),
        type: "POST",
        beforeSend: function (XMLHttpRequest) {
            $("#Loading").show();
        },
        timeout: 0,
        success: function (data) {
            ajaxMessageReader(data, function (data) {
                var resultBody = $("#result-body");
                resultBody.clear();

                $.each(data, function (key, value) {
                    let tr = "<tr>";
                    let keyStr = key.split("/");
                    let fileName = keyStr[keyStr.length - 1];
                    let tdFileName = "<td> " + fileName + "</td>";
                    let tdFilePath = "<td>" + key + "</td>";
                    let tdValue = "<td>" + value + "</td>"
                    let tdRisk ="Safe";
                    let value_float = parseFloat(value);
                    if (value_float <= 0.5) {
                        tdRisk = "High";
                    }else if(value_float <=0.7){
                        tdRisk ="Medium";
                    }else{
                        tdRisk ="Low";
                    }

                    tr+=tdFileName;
                    tr+=tdFilePath;
                    tr+=tdRisk;
                    tr+=tdValue;
                    tr+="</tr>";

                    resultBody.append(tr);
                });
            })
        },
        complete: function (XMLHttpRequest, textStatus) {
            $("#Loading").hide();
        }
    });
}