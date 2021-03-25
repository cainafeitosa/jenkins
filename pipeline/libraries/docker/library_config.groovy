#!/usr/bin/env groovy

fields {
  	optional {
        runs_on = String
		image_repository = String
        dockerfile = String
        context_path = String
  	}
    required {
        registry = String
        credentials_id = String
        image_name = String
    }
}