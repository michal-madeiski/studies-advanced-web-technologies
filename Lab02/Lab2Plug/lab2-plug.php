<?php
/*
Plugin Name: Lab2Plug
Description: Display random announcement before the post.
Version: 1.0
Author: Majkel&Mati
*/

function lab2plug_admin_actions_register_menu() {
    add_options_page(
        "Lab2Plug",
        "Lab2Plug",
        'manage_options',
        "lab2plug",  
        "lab2plug_admin_page"
    );
}
add_action('admin_menu', 'lab2plug_admin_actions_register_menu');

function lab2plug_admin_page() {
    global $_POST;

    if(isset($_POST['lab2plug_do_change'])){
        if($_POST['lab2plug_do_change'] == 'Y'){
            
            if(isset($_POST['lab2plug_announcements']) && is_array($_POST['lab2plug_announcements'])){
                $raw_announcements = wp_unslash($_POST['lab2plug_announcements']); 
                
                $clean_announcements = array();
                foreach($raw_announcements as $announcement){
                    if(trim($announcement) !== ''){
                        $clean_announcements[] = trim($announcement); 
                    }
                }
                update_option('lab2plug_announcements', $clean_announcements);
            } else {
                update_option('lab2plug_announcements', array());
            }

            echo '<div class="notice notice-success is-dismissible"><p>Settings saved.</p></div>';
        }
    }

    $announcements = get_option('lab2plug_announcements', array());
    if(!is_array($announcements)) {
        $announcements = array();
    }

    ?>
    <div class="wrap">
        <h1>Lab2Plug - Announcements settings</h1>
        
        <form name="lab2plug_form" method="post">
            <input type="hidden" name="lab2plug_do_change" value="Y">
            
            <?php 
            foreach($announcements as $index => $ann) { 
            ?>
                <p>
                    <label>Announcement <?php echo $index + 1; ?>:</label><br>
                    <textarea name="lab2plug_announcements[]" style="width:100%; max-width: 800px; height:100px; margin-top:5px;"><?php echo esc_textarea($ann); ?></textarea>
                </p>
            <?php } ?>

            <hr style="max-width: 800px; margin-left: 0;">
            <h3>Add new announcement:</h3>
            <p>
                <textarea name="lab2plug_announcements[]" placeholder="Type HTML code here..." style="width:100%; max-width: 800px; height:100px;"></textarea>
            </p>
            <p><em>To remove announcement clear its field and save.</em></p>

            <p class="submit">
                <input type="submit" class="button button-primary" value="Save">
            </p>
        </form>
    </div>
    <?php
}

function lab2plug_display_random_announcement($content) {
    if (is_single() && is_main_query() && get_post_type() == 'post') {
        
        $announcements = get_option('lab2plug_announcements', array());
        if(!is_array($announcements)) {
            $announcements = array();
        }

        if (!empty($announcements)) {
            $random_key = array_rand($announcements);
            $selected_announcement = $announcements[$random_key];

            $ann_html = '<div class="lab2plug-announcement" style="margin-bottom: 20px;">' . $selected_announcement . '</div>';
            
            $content = $ann_html . $content;
        }
    }
    
    return $content;
}
add_filter('the_content', 'lab2plug_display_random_announcement');

function lab2plug_register_styles(){
    wp_register_style('lab2plug_styles', plugins_url('/css/style.css', __FILE__));
    wp_enqueue_style('lab2plug_styles');
}
add_action('init', 'lab2plug_register_styles');

function lab2plug_display_shortcode() {
    $announcements = get_option('lab2plug_announcements', array());
    if(!is_array($announcements)) {
        $announcements = array();
    }

    if (!empty($announcements)) {
        $random_key = array_rand($announcements);
        $selected_announcement = $announcements[$random_key];

        return '<div class="lab2plug-announcement" style="margin-bottom: 20px; border: 2px dashed #2271b1;">' . $selected_announcement . '</div>';
    }
    
    return '';
}
add_shortcode('random_announcement', 'lab2plug_display_shortcode');
?>