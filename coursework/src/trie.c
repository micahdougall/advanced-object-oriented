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


/**
 * print_trie() - Prints a Trie structure recursively.
 * @node: A pointer to the node in the trie being printed.
 * @indent: Amount to indent the child (depth level).
 * @child_idx: The index of the child node being printed
 * 
 * Works iteratively through the trie and prints each node using print_node(),
 * to which the route of the node is passed.
 */
void print_trie(
	trie_node* node, unsigned int child_idx, unsigned int depth, char* edges
) {
	// Product name string if exists.
	char* product_name = (node -> product)
		? (node -> product) -> name
		: "";
	// Root node as asterisk in graph.
	char digit = (depth == 0)
		? '*'
		: child_idx + '0';

	printf(
		ANSI_COLOR_CYAN "%s" 
		ANSI_COLOR_GREEN_BOLD "%c" 
		ANSI_BOLD_WHITE " %s\n"
		ANSI_COLOR_RESET, 
		edges, digit, product_name
	);

	// Prepare graph edges for all children.
	char* children_edges = (char*) malloc(strlen(edges));
	for (unsigned int i = 0; i < depth * 2; i++) {
		children_edges[i] = (edges[i] == '\\') ? ' ' : edges[i];
	}

	// Get last child index for each parent prior to main loop.
	unsigned int last;
	for (unsigned int j = 0; j < 10; j++) {
		if (node -> children[j]) {
			last = j;
		}
	}

	// Print each child .
	for (unsigned int i = 0; i < 10; i++) {
		if (node -> children[i]) {

			char* child_edges = (char*) malloc(strlen(children_edges) + 1);
			strcpy(child_edges, children_edges);
			strcat(
				child_edges, 
				(i == last) ? " \\" : "|\\"
			);

			print_trie(node -> children[i], i, depth + 1, child_edges);
		}
	}
}


/**
 * lookup_product() - Searches for a specified product in a Trie structure.
 * @node: The root node of the trie to search.
 * @product_code: The unique code of the product to search.
 * 
 * The algorithm is recursive such that the root node provided as an argument
 * need not be the root node of the entire trie structure, but a subset of it.
 * 
 * The product_code indicates the unique address where the product should be 
 * stored within the trie.
 * 
 * Returns: A pointer to the product required, or NULL if no product with the
 * supplied code is found within the trie.
 */
product_t* lookup_product(trie_node* node, unsigned int product_code) {

	if (node -> product && (node -> product) -> code == product_code) {
		return node -> product;
	} else {
		for (unsigned int i = 0; i < 10; i++) {
			if (node -> children[i]) {
				product_t *product = 
					lookup_product(node -> children[i], product_code);

				if (product) {
					return product;
				}
			}
		}
		return NULL;
	}
}


/**
 * insert_into_trie() - Inserts an array or products into a Trie structure.
 * @*root_node: A pointer to the root node of the trie.
 * @*product: An array of products to insert into the trie.
 * 
 * The algorithm uses the unique product.code to insert each product into the
 * trie where each digit in the code denotes the index for a child node from
 * the predceding parent node.
 */
void insert_into_trie(trie_node* root_node, product_t* product) {

	// Create string from numeric code (accounting for number size)
	char code_as_str[(int) log10(product -> code) + 1];
	sprintf(code_as_str, "%i", product -> code);

	trie_node* current_node_ptr = root_node;

	for (unsigned short int c = 0; c < strlen(code_as_str); c++) {
		char code_char = code_as_str[c];
		unsigned short int idx = atoi(&code_char);

		if (!current_node_ptr -> children[idx]) {

			// Create new child node
			trie_node* new_node = (trie_node*) malloc(sizeof(trie_node*));
			trie_node** children = 
				(trie_node**) malloc(sizeof(trie_node**) * 10);

			// Update pointers
			new_node -> children = children;
			current_node_ptr -> children[idx] = new_node;

			*(current_node_ptr -> children[idx]) = *new_node;
		}
		current_node_ptr = current_node_ptr -> children[idx];
	}
	current_node_ptr -> product = product;
}
