#include "main.h"


int main(int argc, char *argv[])
{
	if (argc == 1) {
		printf("Missing required argument for input file.\n");
		exit(EXIT_FAILURE);
	}

	char *file_name = argv[1];
	unsigned int *product_count = (unsigned int *) malloc(sizeof(unsigned int *));

	product_t *products = parse_products_from_file(file_name, product_count);

	print_stock_report(products, *product_count);

	product_t mvp = most_valued_product(products, *product_count);

	// Create root node
	trie_node root_node = { 
		.children = malloc(sizeof(trie_node *) * 10)
	};

	print_if(VERBOSE, "\nInserting %u products in database...\n\n", *product_count);
	for (unsigned int i = 0; i < *product_count; i++) {
		insert_into_trie(&root_node, &products[i]);
	}

	// TODO: Decide whether to print trie or not?
	print_if(VERBOSE, "%s", "\nPrinting trie...\n");
	print_trie(&root_node);

	printf("\nSearching for MVP produdct (%s) in database...\n", mvp.name);

	char search_continue;
	unsigned int search_code = mvp.code;

	do {
		product_t *required_product = lookup_product(&root_node, search_code);

		if (required_product != NULL) {
			printf(
				ANSI_BACKGROUND "\nProduct found with code %u\n"
				ANSI_COLOR_RESET, 
				search_code
			);
			print_product(*required_product);
		} else {
			printf("No product found with code %u!\n", search_code);
		}

		// TODO: Convert to fgets and sscanf for robustness
		printf("\nSearch for another product? (y/n): ");
		scanf(" %c", &search_continue);
		SEARCH_MODE = (search_continue == 'y') ? true : false;
		//TODO: Break from loop instead

		printf("\nPlease enter product code to search: ");
		scanf("%u", &search_code);
	} while (SEARCH_MODE);



	return EXIT_SUCCESS;
}