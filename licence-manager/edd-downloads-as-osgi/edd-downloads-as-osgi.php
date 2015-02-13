<?php
/*
 * Plugin Name: Easy Digital Downloads - Downloads As OSGi licenced bundles
 * Plugin URI:
 * Description: Define downloads as "OSGi licenced bundles". Licenced bundles create a licence page on checkout whch allow a user to generate a licence for their OSGi application. A new licence post is created and linked to from the purchase confirmation page, and in the purchase receipt email
 * Version: 1.0.4
 * Author: Craig Gallen based upon edd-downloads-as-service v 1.0.4 by Andrew Munro, Sumobi
 * Author URI: http://craiggallen.com
 * License: GPL-2.0+
 * License URI: http://www.opensource.org/licenses/gpl-license.php
 */

// Exit if accessed directly
if (! defined ( 'ABSPATH' ))
	exit ();

if (! class_exists ( 'EDD_Downloads_As_Osgi' )) {
	class EDD_Downloads_As_Osgi {
		private static $instance;
		
		/**
		 * Main Instance
		 *
		 * Ensures that only one instance exists in memory at any one
		 * time. Also prevents needing to define globals all over the place.
		 *
		 * @since 1.0
		 *       
		 */
		public static function instance() {
			if (! isset ( self::$instance )) {
				self::$instance = new self ();
			}
			
			return self::$instance;
		}
		
		/**
		 * Start your engines
		 *
		 * @since 1.0
		 *       
		 * @return void
		 */
		public function __construct() {
			$this->setup_globals ();
			$this->setup_actions ();
			$this->load_textdomain ();
		}
		
		/**
		 * Globals
		 *
		 * @since 1.0
		 *       
		 * @return void
		 */
		private function setup_globals() {
			// paths
			$this->file = __FILE__;
			$this->basename = apply_filters ( 'edd_osgi_plugin_basenname', plugin_basename ( $this->file ) );
			$this->plugin_dir = apply_filters ( 'edd_osgi_plugin_dir_path', plugin_dir_path ( $this->file ) );
			$this->plugin_url = apply_filters ( 'edd_osgi_plugin_dir_url', plugin_dir_url ( $this->file ) );
		}
		
		/**
		 * Setup the default hooks and actions
		 *
		 * @since 1.0
		 *       
		 * @return void
		 */
		private function setup_actions() {
			global $edd_options;
			
			// metabox
			add_action ( 'edd_meta_box_settings_fields', array (
					$this,
					'add_metabox' 
			) );
			add_action ( 'edd_metabox_fields_save', array (
					$this,
					'save_metabox' 
			) );
			
			// actions added to checkout template
			
			// these only change the test surrounding the checkout button
			// add actions before checkout
			// add_action( 'edd_purchase_form_before_submit', array( $this, 'save_metabox' ) );
			
			// add actions after checkout
			// add_action( 'edd_purchase_form_after_submit', array( $this, 'save_metabox' ) );
			
			// hopefully adds content to the success page
			// SEE \easy-digital-downloads\includes\checkout\template.php
			// add_filter( 'the_content', array($this, 'edd_osgi_filter_success_page_osgi') );
			
			// see \easy-digital-downloads\templates\shortcode-receipt.php
			add_action ( 'edd_payment_receipt_after_table', array (
					$this,
					'edd_osgi_action_payment_receipt_after_table' 
			) );
			
			// add_action( 'edd_payment_receipt_after', array( $this, 'edd_osgi_action_payment_receipt_after_table' ) );
			
			// settings
			add_filter ( 'edd_settings_extensions', array (
					$this,
					'settings' 
			) );
			
			// filter each download
			add_filter ( 'edd_receipt_show_download_files', array (
					$this,
					'receipt' 
			), 10, 2 );
			add_filter ( 'edd_email_receipt_download_title', array (
					$this,
					'email_receipt' 
			), 10, 3 );
			
			do_action ( 'edd_osgi_setup_actions' );
		}
		
		/**
		 * 
		 * @param unknown $payment
		 * @param string $edd_receipt_args
		 */
		public function edd_osgi_action_payment_receipt_after_table($payment, $edd_receipt_args = null) {
			echo "<P>CGALLEN this is the action after table</P>";
			if (isset ( $payment )) {
				echo "<p>Payment vardump=";
				var_dump ( $payment );
				echo "</P>";
				echo "<p>Payment metadata vardump=";
				$meta = get_post_meta ( $payment->ID );
				var_dump ( $meta );
				echo "</P>";
				$edd_payment_number = ( string ) $meta ['_edd_payment_number'] [0];
				if (isset ( $edd_payment_number )) {
					echo "<p>edd_payment_number=";
					echo $edd_payment_number;
					echo "</P>";
					
					$found_post = null;
					
					if ($posts = get_posts ( array (
							'name' => $edd_payment_number,
							'post_type' => 'post',
							'post_status' => 'publish',
							'posts_per_page' => 1 
					) ))
						$found_post = $posts [0];
						
						// Now, we can do something with $found_post
					if (! is_null ( $found_post )) {
						echo "<p>we found the post=";
						echo $found_post->ID;
						echo '<BR><a href="'. get_permalink( $found_post->ID ) . '" >My link to a post or page</a>';
						echo "</P>";
					} else {
						
						// get post with payment number metadata OR create post with metadata
						
						$post = array (
								// 'ID' => [ <post id> ] // Are you updating an existing post?
								'post_content' => '<p>post content</p>', // The full text of the post.
								'post_name' => $edd_payment_number, // The name (slug) for your post
								'post_title' => $edd_payment_number, // The title of your post.
								                                         // 'post_status' => [ 'draft' | 'publish' | 'pending'| 'future' | 'private' | custom registered status ] // Default 'draft'.
								'post_status' => 'publish', // Default 'draft'.
								                               // 'post_type' => [ 'post' | 'page' | 'link' | 'nav_menu_item' | custom post type ] // Default 'post'.
								                               // 'post_author' => [ <user ID> ] // The user ID number of the author. Default is the current user ID.
								'ping_status' => 'closed', // Pingbacks or trackbacks allowed. Default is the option 'default_ping_status'.
								                              // 'post_parent' => [ <post ID> ] // Sets the parent of the new post, if any. Default 0.
								                              // 'menu_order' => [ <order> ] // If new post is a page, sets the order in which it should appear in supported menus. Default 0.
								                              // 'to_ping' => // Space or carriage return-separated list of URLs to ping. Default empty string.
								                              // 'pinged' => // Space or carriage return-separated list of URLs that have been pinged. Default empty string.
								                              // 'post_password' => [ <string> ] // Password for post, if any. Default empty string.
								                              // 'guid' => // Skip this and let Wordpress handle it, usually.
								                              // /'post_content_filtered' => // Skip this and let Wordpress handle it, usually.
								                              // /'post_excerpt' => [ <string> ] // For all your post excerpt needs.
								                              // 'post_date' => [ Y-m-d H:i:s ] // The time post was made.
								                              // 'post_date_gmt' => [ Y-m-d H:i:s ] // The time post was made, in GMT.
								'comment_status' => 'closed' 
						 // Default is the option 'default_comment_status', or 'closed'.
						                             // 'post_category' => [ array(<category id>, ...) ] // Default empty.
						                             // 'tags_input' => [ '<tag>, <tag>, ...' | array ] // Default empty.
						                             // 'tax_input' => [ array( <taxonomy> => <array | string> ) ] // For custom taxonomies. Default empty.
						        //'page_template' => '../edd-downloads-as-osgi.php' // Requires name of template file, eg template.php. Default empty.
						);
						
						$newpost_id = wp_insert_post ( $post );
						
						echo "<p>we created a new post=";
						echo $newpost_id;
						echo '<BR><a href="'. get_permalink( $newpost_id ) . '" >My link to a post or page</a>';
						echo "</P>";
					}
				}
			} else
				echo '<P>payment not set</P>';
			
			if (isset ( $edd_receipt_args )) {
				echo "<p>edd_receipt_args vardump=";
				var_dump ( $edd_receipt_args );
				echo "</P>";
			} else
				echo '<P>edd_receipt_args not set</P>';
		}
		
		/**
		 * TODO REMOVE
		 */
		public function edd_osgi_filter_success_page_osgi($content) {
			global $edd_options, $edd_receipt_args;
			
			if (isset ( $edd_options ['success_page'] )) {
				$content = $content . "<p>CRAIG TEST success_page</p>";
			}
			if (isset ( $_GET ['payment-confirmation'] )) {
				$content = $content . "<p>CRAIG TEST payment-confirmation</p>";
			}
			if (is_page ( $edd_options ['success_page'] )) {
				$content = $content . "<p>CRAIG TEST is_page success_page</p>";
			}
			
			if (isset ( $edd_options ['success_page'] ) && isset ( $_GET ['payment-confirmation'] ) && is_page ( $edd_options ['success_page'] )) {
				// if ( has_filter( 'edd_payment_confirm_' . $_GET['payment-confirmation'] ) ) {
				// $content = apply_filters( 'edd_payment_confirm_' . $_GET['payment-confirmation'], $content );
				// }
				$content = $content . "<p>CRAIG TEST ADDTIONAL CONTENT CHECKOUT</p>";
			}
			
			$anID = $edd_receipt_args ['id'];
			$content = $content . "<p>CRAIG TEST edd_receipt_args[id]={$anID}</p>";
			
			$the_payment = get_post ( $edd_receipt_args ['id'] );
			$id = $the_payment->ID;
			$content = $content . "<p>CRAIG TEST the_payment->ID={$the_payment->ID}</p>";
			
			$my_value = get_post_meta ( $id, '_edd_payment_number', 1 );
			
			$content = $content . "<p>CRAIG TEST my_value->$my_value</p>";
			
			$sw_args = array (
					'meta_query' => array (
							array (
									'key' => 'edd_osgi_licence',
									'value' => $my_value,
									'compare' => 'LIKE' 
							) 
					) 
			);
			$query = new WP_Query ( $sw_args );
			// if ( $the_query->have_posts() ) {
			// echo '<h2>Films By Star Wards Directors</h2>';
			// echo '<ul>';
			// while ( $the_query->have_posts() ) {
			// $the_query->the_post();
			// echo '<li>' . get_the_title() . '</li>';
			// }
			// echo '</ul>';
			// }
			// /* Restore original Post Data */
			// wp_reset_postdata();
			
			return $content;
		}
		
		/**
		 * Loads the plugin language files
		 *
		 * @access public
		 * @since 1.0
		 * @return void
		 */
		public function load_textdomain() {
			// Set filter for plugin's languages directory
			$lang_dir = dirname ( plugin_basename ( $this->file ) ) . '/languages/';
			$lang_dir = apply_filters ( 'edd_osgi_languages_directory', $lang_dir );
			
			// Traditional WordPress plugin locale filter
			$locale = apply_filters ( 'plugin_locale', get_locale (), 'edd-osgi' );
			$mofile = sprintf ( '%1$s-%2$s.mo', 'edd-osgi', $locale );
			
			// Setup paths to current locale file
			$mofile_local = $lang_dir . $mofile;
			$mofile_global = WP_LANG_DIR . '/edd-downloads-as-osgi/' . $mofile;
			
			if (file_exists ( $mofile_global )) {
				load_textdomain ( 'edd-osgi', $mofile_global );
			} elseif (file_exists ( $mofile_local )) {
				load_textdomain ( 'edd-osgi', $mofile_local );
			} else {
				// Load the default language files
				load_plugin_textdomain ( 'edd-osgi', false, $lang_dir );
			}
		}
		
		/**
		 * Add Metabox if per download email attachments are enabled
		 *
		 * @since 1.0
		 */
		public function add_metabox($post_id) {
			$checked = ( boolean ) get_post_meta ( $post_id, '_edd_osgi_enabled', true );
			$edd_osgiProductIdStr = ( string ) get_post_meta ( $post_id, 'edd_osgiProductIdStr', true );
			?>
<p>
	<strong><?php apply_filters( 'edd_osgi_header', printf( __( '%s As OSGi Licence:', 'edd-osgi' ), edd_get_label_singular() ) ); ?></strong>
</p>
<p>
	<label for="edd_download_as_osgi"> <input type="checkbox"
		name="_edd_osgi_enabled" id="edd_download_as_osgi" value="1"
		<?php checked( true, $checked ); ?> />
					<?php apply_filters( 'edd_osgi_header_label', printf( __( 'This %s is an OSGi licenced module', 'edd-osgi' ), strtolower( edd_get_label_singular() ) ) ); ?>
				</label>
</p>
<p>
	<strong>Enter OSGi Product Id</strong>
</p>
<p>
	<input type="text" name="edd_osgiProductIdStr"
		id="xedd_osgiProductIdStr"
		value="<?php echo $edd_osgiProductIdStr; ?>" />
</p>
<?php
		}
		
		/**
		 * Add to save function
		 * 
		 * @param $fields Array
		 *        	of fields
		 * @since 1.0
		 * @return array
		 */
		public function save_metabox($fields) {
			$fields [0] = '_edd_osgi_enabled';
			$fields [1] = 'edd_osgiProductIdStr';
			return $fields;
		}
		
		/**
		 * Prevent receipt from listing download files
		 * 
		 * @param $enabled default
		 *        	true
		 * @param int $item_id
		 *        	ID of download
		 * @since 1.0
		 * @return boolean
		 */
		public function receipt($enabled, $item_id) {
			if ($this->is_osgi ( $item_id )) {
				return false;
			}
			
			return true;
		}
		
		/**
		 * Modify email template to remove dash if the item is a service
		 *
		 * @since 1.0
		 */
		// TODO CHANGE
		public function email_receipt($title, $item_id, $price_id) {
			if ($this->is_osgi ( $item_id )) {
				$title = get_the_title ( $item_id );
				
				if ($price_id !== false) {
					$title .= "&nbsp;" . edd_get_price_option_name ( $item_id, $price_id );
				}
			}
			
			return $title;
		}
		
		/**
		 * Is OSGi
		 * 
		 * @param int $item_id
		 *        	ID of download
		 * @return boolean true if service, false otherwise
		 * @return boolean
		 */
		public function is_osgi($item_id) {
			global $edd_receipt_args, $edd_options;
			
			// get array of osgi categories
			$osgi_categories = isset ( $edd_options ['edd_osgi_osgi_categories'] ) ? $edd_options ['edd_osgi_osgi_categories'] : '';
			
			$term_ids = array ();
			
			if ($osgi_categories) {
				foreach ( $osgi_categories as $term_id => $term_name ) {
					$term_ids [] = $term_id;
				}
			}
			
			$is_osgi = get_post_meta ( $item_id, '_edd_osgi_enabled', true );
			
			// get payment
			$payment = get_post ( $edd_receipt_args ['id'] );
			$meta = isset ( $payment ) ? edd_get_payment_meta ( $payment->ID ) : '';
			$cart = isset ( $payment ) ? edd_get_payment_meta_cart_details ( $payment->ID, true ) : '';
			
			if ($cart) {
				foreach ( $cart as $key => $item ) {
					$price_id = edd_get_cart_item_price_id ( $item );
					
					$download_files = edd_get_download_files ( $item_id, $price_id );
					
					// if the service has a file attached, we still want to show it
					if ($download_files)
						return;
				}
			}
			
			// check if download has meta key or has a service term assigned to it
			if ($is_osgi || has_term ( $term_ids, 'download_category', $item_id )) {
				return true;
			}
			
			return false;
		}
		
		/**
		 * Get terms
		 * 
		 * @return array
		 * @since 1.0
		 */
		public function get_terms() {
			$args = array (
					'hide_empty' => false,
					'hierarchical' => false 
			);
			
			$terms = get_terms ( 'download_category', apply_filters ( 'edd_osgi_get_terms', $args ) );
			
			$terms_array = array ();
			
			foreach ( $terms as $term ) {
				$term_id = $term->term_id;
				$term_name = $term->name;
				
				$terms_array [$term_id] = $term_name;
			}
			
			if ($terms)
				return $terms_array;
			
			return false;
		}
		
		/**
		 * Settings
		 *
		 * @since 1.0
		 */
		public function settings($settings) {
			$new_settings = array (
					array (
							'id' => 'edd_osgi_header',
							'name' => '<strong>' . __ ( 'Downloads As OSGi Licenced Bundles', 'edd-osgi' ) . '</strong>',
							'type' => 'header' 
					),
					array (
							'id' => 'edd_osgi_service_categories',
							'name' => __ ( 'Select OSGi licence Categories', 'edd-osgi' ),
							'desc' => __ ( 'Select the categories that contain "OSGi Licences"', 'edd-osgi' ),
							'type' => 'multicheck',
							'options' => $this->get_terms () 
					) 
			);
			
			return array_merge ( $settings, $new_settings );
		}
	}
}

/**
 * Get everything running
 *
 * @since 1.0
 *       
 * @access private
 * @return void
 */
function edd_downloads_as_osgi() {
	$edd_downloads_as_osgi = new EDD_Downloads_As_Osgi ();
}
add_action ( 'plugins_loaded', 'edd_downloads_as_osgi' );