package ru.zarwlad.permissions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RolePermissionMapper {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Введи путь к файлу с пермишенами");
        Path permissionPath = Paths.get(reader.readLine());
        System.out.println("Введи путь к файлу с role-permission");
        Path rootRolePermPath = Paths.get(reader.readLine());

        Set<String> permissions = new HashSet<>();

        List<String> permList = Files.readAllLines(permissionPath);
        for (String s : permList) {
            String perm = s.split(";")[0];
            if (permissions.contains(perm))
                System.out.printf("\nОбнаружено задвоение пермишенов! Пермишен: %s", perm);
            permissions.add(perm);
        }

        Set<String> rootPermissions = new HashSet<>();

        List<String> strings = Files.readAllLines(rootRolePermPath);
        List<String> rootPermsList = strings;
        for (String s : rootPermsList) {
            String rootPerm = s.split(";")[1];
            if (rootPermissions.contains(rootPerm))
                System.out.printf("\nОбнаружено задвоение role-permission для рута! Пермишен: %s", rootPerm);

            rootPermissions.add(rootPerm);
        }

        for (String permission : permissions) {
            if (!rootPermissions.contains(permission) && !permission.equals("id"))
                System.out.printf("\nПермишен не обнаружен в role-permissions для рута! Пермишен: %s", permission);
        }

        for (String rootPermission : rootPermissions) {
            if (!permissions.contains(rootPermission) && !rootPermission.equals("permission_id"))
                System.out.printf("\nДля role-permission у рута обнаружен " +
                        "несуществующий пермишен! Пермишен: %s", rootPermission);
        }

        System.out.printf("\nВсего пермишенов: %d, всего ролей-пермишенов: %d", permissions.size(), rootPermissions.size());
    }
}
