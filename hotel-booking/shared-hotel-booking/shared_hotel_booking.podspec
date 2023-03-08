Pod::Spec.new do |spec|
    spec.name                     = 'shared_hotel_booking'
    spec.version                  = '0.1.0'
    spec.homepage                 = 'https://github.com/pablichjenkov/templato'
    spec.source                   = { :http=> ''}
    spec.authors                  = ''
    spec.license                  = ''
    spec.summary                  = 'Shared code for the Hello World example'
    spec.vendored_frameworks      = 'build/cocoapods/framework/HotelBookingKt.framework'
    spec.libraries                = 'c++'
    spec.ios.deployment_target = '14.1'
                
                
    spec.pod_target_xcconfig = {
        'KOTLIN_PROJECT_PATH' => ':shared-hotel-booking',
        'PRODUCT_MODULE_NAME' => 'HotelBookingKt',
    }
                
    spec.script_phases = [
        {
            :name => 'Build shared_hotel_booking',
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