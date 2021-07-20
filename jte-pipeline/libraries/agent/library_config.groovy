fields { 
    required {}
    optional {
        kubernetes {
            cloud = String
            inherit_from = String
        }
        node {
            label = String
        }
    }
}