<?php
/**
 * Plugin Name:       My plugin
 * Plugin URI:        https://example.com/plugins/my-plugin/
 * Description:       Displays random announcements at the beginning of each post.
 * Version:           1.0
 * Requires at least: 5.0
 * Requires PHP:      7.2
 * Author:            Mateusz Janiszewski
 */


function mp_register_menu() {
    add_options_page(
        "Lab 1 plugin",
        "Lab 1 plugin",
        'manage_options',
        "pa",
        "mp_admin_page"
    );
}
add_action('admin_menu', 'mp_register_menu');

function mp_admin_styles() {
    wp_enqueue_style('mp_admin_styles', plugins_url('/css/admin-style.css', __FILE__));
    wp_enqueue_code_editor(['type' => 'text/html']);
}
add_action('admin_enqueue_scripts', 'mp_admin_styles');

function mp_frontend_styles() {
    wp_enqueue_style('mp_styles', plugins_url('/css/style.css', __FILE__));
}
add_action('wp_enqueue_scripts', 'mp_frontend_styles');

// To cudo do pola do edycji ogłoszenia w html
function mp_admin_scripts() {
    $settings = wp_enqueue_code_editor(['type' => 'text/html']);
    wp_add_inline_script(
        'code-editor',
        sprintf('jQuery(function() { wp.codeEditor.initialize(document.getElementById("announcement"), %s); });',
        wp_json_encode($settings))
    );
}
add_action('admin_enqueue_scripts', 'mp_admin_scripts');


function mp_admin_page() {
    $announcements = get_option('mp_announcements', []);

    if (isset($_POST['announcement'])) {
        $announcements[] = wp_kses_post($_POST['announcement']);
        update_option('mp_announcements', $announcements);
    }

    if (isset($_POST['delete_index'])) {
        unset($announcements[$_POST['delete_index']]);
        update_option('mp_announcements', $announcements);
    }
    ?>
    <h1 class="mp-header">Your Announcements:</h1>
    <table class="mp-table">
        <thead>
            <tr>
                <th>Announcement content</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
            <?php foreach ($announcements as $index => $announcement) { ?>
                <tr>
                    <td><?php echo $announcement ?></td>
                    <td>
                        <form method="post">
                            <input type="hidden" name="delete_index" value="<?php echo $index ?>">
                            <input type="submit" value="Delete">
                        </form>
                    </td>
                </tr>
            <?php } ?>
        </tbody>
    </table>

    <h1 class="mp-header">Add new announcement:</h1>
    <form method="post">
        <textarea id="announcement" name="announcement" rows="4" cols="50" placeholder="Enter announcement content in HTML, e.g. <b>Warning!</b>"></textarea>
        <input type="submit" value="Save">
    </form>
    <?php
}


function mp_inject_announcement($content) {
    $announcements = get_option('mp_announcements', []);
    if (empty($announcements)) {
        return $content;
    }
    $random = $announcements[array_rand($announcements)];
    return '<div class="mp-announcement">' . $random . '</div>' . $content;
}
add_filter("the_content", "mp_inject_announcement");


function mp_shortcode_handler() {
    $announcements = get_option('mp_announcements', []);
    if (empty($announcements)) {
        return '';
    }
    $random = $announcements[array_rand($announcements)];
    return '<div class="mp-announcement">' . $random . '</div>';
}
add_shortcode('announcement', 'mp_shortcode_handler');