resolvers in ThisBuild += "lightbend-commercial-mvn" at
  "https://repo.lightbend.com/pass/P93W4KP0ygfwEFzT11tUoX2ywn7pozKSOjClRAcTRdSvvs4V/commercial-releases"
resolvers in ThisBuild += Resolver.url("lightbend-commercial-ivy",
  url("https://repo.lightbend.com/pass/P93W4KP0ygfwEFzT11tUoX2ywn7pozKSOjClRAcTRdSvvs4V/commercial-releases"))(Resolver.ivyStylePatterns)