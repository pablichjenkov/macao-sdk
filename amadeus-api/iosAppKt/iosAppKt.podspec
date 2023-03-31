Pod::Spec.new do |spec|
    spec.name                     = 'iosAppKt'
    spec.version                  = '1.0-SNAPSHOT'
    spec.homepage                 = 'https://github.com/pablichjenkov/templato'
    spec.source                   = { :http=> ''}
    spec.authors                  = ''
    spec.license                  = ''
    spec.summary                  = 'Amadeus API umbrella module'
    spec.vendored_frameworks      = 'build/cocoapods/framework/AmadeusDemoKt.framework'
    spec.libraries                = 'c++'
    spec.ios.deployment_target = '16.0'
                
                
    spec.pod_target_xcconfig = {
        'KOTLIN_PROJECT_PATH' => ':iosAppKt',
        'PRODUCT_MODULE_NAME' => 'AmadeusDemoKt',
    }
                
    spec.script_phases = [
        {
            :name => 'Build iosAppKt',
            :execution_position => :before_compile,
            :shell_path => '/bin/sh',
            :script => <<-SCRIPT
                if [ "YES" = "$OVERRIDE_KOTLIN_BUILD_IDE_SUPPORTED" ]; then
                  echo "Skipping Gradle build task invocation due to OVERRIDE_KOTLIN_BUILD_IDE_SUPPORTED environment variable set to \"YES\""
                  exit 0
                fi
                set -ev
                REPO_ROOT="$PODS_TARGET_SRCROOT"
                "$REPO_ROOT/../gradlew" -p "$REPO_ROOT" $KOTLIN_PROJECT_PATH:syncFramework \
                    -Pkotlin.native.cocoapods.platform=$PLATFORM_NAME \
                    -Pkotlin.native.cocoapods.archs="$ARCHS" \
                    -Pkotlin.native.cocoapods.configuration="$CONFIGURATION"
            SCRIPT
        }
    ]
    spec.resources = ['src/commonMain/resources/**', 'src/iosMain/resources/**']
end