#include "main.h"

#define MAX_FILENAME_LEN 50


int main(int argc, char* argv[])
{
	// Check CLI args for required filename
	if (argc == 1) {
		printf("Missing required argument for input file.\n");
		exit(EXIT_FAILURE);
	}
	char file_name[MAX_FILENAME_LEN + 4] = "res/";;
	strcat(file_name, argv[1]);

	// Check CLI for verbose flag
	for (unsigned int i = 2; i < (argc); i++) {
		char option = *argv[i];
		switch (option) {
			case 'V':
				VERBOSE = true;
				break;
		}
	}

	// Read products from file (pointer to products is constant).
	unsigned int* product_count = (unsigned int*) malloc(sizeof(unsigned int*));
	product_t* const products = parse_products_from_file(file_name, product_count);

	// Print report / sample report.
	print_stock_report(products,*product_count);

	// Calculate best product score.
	product_t mvp = most_valued_product(products, *product_count);

	// Trie insertion, create root node (const pointer - root never changes).
	trie_node* const root_node = (trie_node*) malloc(sizeof(trie_node*));
	root_node -> children = 
		(trie_node**) malloc(sizeof(trie_node**) * 10);

	print_if(VERBOSE, "\nTrie DB insert (%u products)...", *product_count);
	for (unsigned int i = 0; i < *product_count; i++) {
		insert_into_trie(root_node, &products[i]);
	}
	if (VERBOSE) {
		printf("complete:\n\n");
		char* edges = "";
	 	print_trie(root_node, 0, 0, edges);
	}

	// Auto print MVP
	printf("\nSearching for MVP produdct (%s) in database...\n", mvp.name);
	search_product(root_node, mvp.code);

	// User search loop.
	user_product_search(root_node);

	// Clear memory
	free_the_children(root_node);
	free(root_node);
	free(products);

	return EXIT_SUCCESS;
}


void search_product(trie_node* root_node, unsigned int search_code) {
	product_t* required_product = lookup_product(root_node, search_code);

	if (required_product != NULL) {
		printf(
			ANSI_BACKGROUND "\nProduct found with code %u"
			ANSI_COLOR_RESET "\n", 
			search_code
		);
		print_product(*required_product);
	} else {
		printf("No product found with code %u!\n", search_code);
	}
}


void user_product_search(trie_node* root_node) {
	char search_continue;
	do {
		unsigned int search_code;
		printf("\nSearch for another product? (y/n): ");
		scanf(" %c", &search_continue);

		if (search_continue == 'y') {
			printf("\nPlease enter product code to search: ");
			scanf("%u", &search_code);
			search_product(root_node, search_code);
		}
	} while (search_continue == 'y');
}




