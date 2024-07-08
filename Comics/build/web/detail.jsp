<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Story Detail Page</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
          integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z"
          crossorigin="anonymous">
    <!-- Your custom CSS -->
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <div class="container">
        <h1>Story Detail</h1>
        <div class="card">
            <div class="card-body">
                <h5 class="card-title">${story.title}</h5>
                <p class="card-text">${story.description}</p>
                <p class="card-text"><strong>Author:</strong> ${story.authorName}</p>
                <p class="card-text"><strong>Genre:</strong> ${story.genreName}</p>
                <p class="card-text"><strong>Status:</strong> ${story.status}</p>
                <p class="card-text"><strong>Created At:</strong> ${story.createdAt}</p>
                <!-- Add more details as needed -->
            </div>
        </div>
    </div>

    <!-- Bootstrap JS and dependencies -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
            integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"
            integrity="sha384-Q2m5gnhR1HvxgyZYU4Xsgt9M7NpFscGE7O56njP7ZM2Jibls/AOoEMD6IagDmFj1"
            crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"
            integrity="sha384-8aIt2IQN6FV/xJnUGoUpfzNYSgXD2Hc5PLvVjmF452bYIvtxkhIW1QY8jSN6RSJo"
            crossorigin="anonymous"></script>
</body>
</html>