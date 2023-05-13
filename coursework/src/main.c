#include "main.h"


int main(int argc, char *argv[])
{
	// Check CLI args for file, verbose
	if (argc == 1) {
		printf("Missing required argument for input file.\n");
		exit(EXIT_FAILURE);
	}
	char *file_name = argv[1];
	for (unsigned int i = 2; i < (argc); i++) {
		printf("%s\n", argv[i]);
		char option = *argv[i];
		switch (option) {
			case 'V':
				VERBOSE = true;
				break;
		}
	}

	// Read products from file.
	unsigned int *product_count = (unsigned int *) malloc(sizeof(unsigned int *));
	product_t *products = parse_products_from_file(file_name, product_count);

	// Print report / sample report.
	print_stock_report(products, *product_count);

	// Calculate best product score.
	product_t mvp = most_valued_product(products, *product_count);

	// Trie insertion, create root node.
	trie_node root_node = { 
		.children = malloc(sizeof(trie_node *) * 10)
	};
	print_if(VERBOSE, "\nTrie DB insert (%u products)...", *product_count);
	for (unsigned int i = 0; i < *product_count; i++) {
		insert_into_trie(&root_node, &products[i]);
	}
	if (VERBOSE) {
		printf("complete:\n\n");
		char* edges = "";
	 	print_trie(&root_node, 0, 0, edges);
	}

	// Auto print MVP
	printf("\nSearching for MVP produdct (%s) in database...\n", mvp.name);
	char search_continue;
	unsigned int search_code = mvp.code;

	do {
		product_t *required_product = lookup_product(&root_node, search_code);

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

	// 	// TODO: Convert to fgets and sscanf for robustness
	// 	printf("\nSearch for another product? (y/n): ");
	// 	scanf(" %c", &search_continue);
	// 	SEARCH_MODE = (search_continue == 'y') ? true : false;
	// 	//TODO: Break from loop instead

	// 	printf("\nPlease enter product code to search: ");
	// 	scanf("%u", &search_code);
	} while (SEARCH_MODE);



	return EXIT_SUCCESS;
}