<!doctype html>

<html lang="en">
<head>
    <meta charset="utf-8"/>
    <title>jQuery UI Draggable - Default functionality</title>
    <link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css"/>
    <link rel="stylesheet" href="css/demo_page.css"/>
    <link rel="stylesheet" href="css/demo_table.css"/>
    <script src="js/lib/jquery-1.9.1.js"></script>
    <script src="js/lib/jquery-ui.js"></script>
    <script src="js/lib/jquery.dataTables.js"></script>
    <script src="js/lib/jquery.jeditable.js"></script>
    <script src="js/lib/jquery.jeditable.datepicker.js"></script>
    <script src="js/lib/jquery.json-2.4.js"></script>
    <script src="js/balance.js"></script>
    <script src="js/basket.js"></script>
    <script src="js/currency.js"></script>
    <script src="js/category.js"></script>
    <script>
        $(document).ready(function () {
            var balance = new Balance($('#example'));
            balance.load(balance, '${user_uid}');
        });
    </script>
</head>
<body id="dt_example">

<table cellpadding="0" cellspacing="0" border="0" class="display dataTable" id="example" aria-describedby="example_info">
    <thead>
    <tr id="basket_title"></tr>
    </thead>
    <tbody>
    <tr id="basket_table"></tr>
    </tbody>
</table>

</body>
</html>