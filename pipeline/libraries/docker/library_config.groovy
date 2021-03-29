#!/usr/bin/env groovy

fields {
  	optional {
        pod_template = String
        registry = String
        credentials_id = String
		image_repository = String
        dockerfile = String
        context_path = String
  	}
    required {
        image_name = String
    }
}