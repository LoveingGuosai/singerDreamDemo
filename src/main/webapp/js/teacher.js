/**
 * Created by user on 15-7-31.
 */
$(document).ajaxError(function (event, xhr, settings) {
    if (xhr.status == 403) {
        window.location.href = 'login.html';
    }
});
riot.mount('navigation', 'navigate');
$(
    function () {
        var endpoint = '/api/backend/teachers/findByCurrentManager';
        var size = 10, currentPage = 0, pageObj, pager;
        var teachers, selectIndex;
        var container = $('#teachers_container');
        pager = riot.mount('.pagination', 'pager', {endpoint: endpoint, parameters: {size: size, sort: 'id,asc'}})[0];

        pager.on('page_data', function (data) {
            if (data.content) {
                updateElements(data);
            } else {
                container.empty();
            }
        });
        pager.goto(0);

        function updateElements(data) {
            teachers = data.content;
            pageObj = data;
            currentPage = pageObj.number;
            container.empty();


            teachers.forEach(function (teacher, i) {
                container.append(createTeacherElement(teacher, i));
            });
            pager.update();
        }

        function createTeacherElement(teacher, i) {
            var elementHtml = ' <tr> <td>'+teacher.name+'</td> ' +
                '<td>'+teacher.phoneNumber+'</td> ' +
                '<td> <button type="button" class="btn btn-primary btn-xs" data-toggle="modal" data-target="#myModal">' +
                '<i class="glyphicon glyphicon-edit"></i> 修改</button> ' +
                '<button type="button" class="btn btn-default btn-xs">' +
                '<i class="glyphicon glyphicon-trash"></i></button> ' +
                '</td> ' +
                '</tr>';
            return elementHtml;
        }

    }
);