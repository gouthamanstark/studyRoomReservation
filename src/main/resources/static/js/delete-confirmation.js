 $(document).ready(function() {
        $('#deleteStudent').click(function() {
            var studentId = $(this).data('student-id');
            var deleteLink = $('#deleteLink');
            
            var deleteUrl = deleteLink.attr('href');
            deleteLink.attr('href', deleteUrl +"?universityID=" +studentId);
            console.log(deleteLink.attr('href'));
        });
    });
