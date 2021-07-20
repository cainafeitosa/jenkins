fields { 
    required {}
    optional {
        agent {
            image = String
        }
        work_dir          = String
        registry          = String
        node_installation = String
        skip_test         = Boolean
    }
}