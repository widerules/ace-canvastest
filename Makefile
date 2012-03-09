TOOLS=/opt/android/platform-tools
ADB=$(TOOLS)/adb
APP=app_initial

all:
	ant debug

clean:
	ant clean

test: all
	$(ADB) -d install -r bin/$(APP)-debug.apk

log:
	$(ADB) logcat -s MyLog:*
