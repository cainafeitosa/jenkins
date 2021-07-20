fields { 
    required {}
    optional {
        agent {
            image = String
        }
        work_dir          = String
        mvn_installation  = String
        jdk_installation  = String
        mvn_settings      = String
        cli_options       = String
        skip_test         = Boolean
        test_results_path = String
    }
}