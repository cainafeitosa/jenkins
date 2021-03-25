#!/usr/bin/env groovy

fields {
  	optional {
		runs_on {
			kubernetes {
				cloud = String
				pod_template = String
			}
		}
		cli_options = String
		test_results_path = String
  	}
}