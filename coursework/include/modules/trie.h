#include <math.h>


/**
 * Trie - Stores a product along with its calculated score.
 * @children: Array of pointers to other trie_node objects.
 * @product: A pointer to a product attached to this node.
 * 
 * Simplified with typedef product_ext.
 */
typedef struct Trie {
	struct Trie** children;
	product_t* product;
} trie_node;


void insert_into_trie(trie_node* root_node, product_t* product);
void print_trie(
	trie_node* node, unsigned int child_idx, unsigned int depth, char* edges
);
product_t* lookup_product(trie_node* node, unsigned int product_code);
void free_the_children(trie_node* node);