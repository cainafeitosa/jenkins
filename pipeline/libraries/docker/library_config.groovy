#!/usr/bin/env groovy

fields {
  	optional {
        pod_template = String
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