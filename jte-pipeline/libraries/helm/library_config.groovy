fields { 
    required {
        registry       = String
        repository     = String
        credentials_id = String
    }
    optional {
        agent {
            image = String
        }
        chart_path = String
    }
}