[Setup]
AppName=M2MConnect
AppVersion=1.0.0
DefaultDirName={pf}\M2MConnect
DefaultGroupName=M2MConnect
UninstallDisplayIcon={app}\M2MConnect.exe
Compression=lzma2/ultra
OutputDir=..\InnoSetup
OutputBaseFilename=M2MConnect_Setup


[Languages]
Name: "french"; MessagesFile: "compiler:Languages\French.isl"
Name: "english"; MessagesFile: "compiler:Default.isl"
Name: "dutch"; MessagesFile: "compiler:Languages\Dutch.isl"
Name: "german"; MessagesFile: "compiler:Languages\German.isl"

[Tasks]
Name: "desktopicon"; Description: "{cm:CreateDesktopIcon}"; \
    GroupDescription: "{cm:AdditionalIcons}"; Flags: unchecked

[Files]
Source: "..\exe\target\M2MConnect.exe"; DestDir: "{app}"
Source: "..\InnoSetup\M2MConnect.ico"; DestDir: "{app}"
Source: "..\InnoSetup\JRE\*"; DestDir: "{app}\JRE"; Flags: recursesubdirs


[Icons]
Name: "{group}\M2MConnect"; Filename: "{app}\M2MConnect.exe"
Name: "{commondesktop}\M2MConnect"; Filename: "{app}\M2MConnect.exe"; IconFilename: "{app}\M2MConnect.ico"; Tasks: desktopicon

[Run]
Filename: "{app}\M2MConnect.exe"; Description: "{cm:LaunchProgram, M2MConnect}"; Flags: nowait postinstall skipifsilent runascurrentuser

[UninstallRun]
Filename: "{app}\unins000.exe"; Parameters: "/SILENT"

