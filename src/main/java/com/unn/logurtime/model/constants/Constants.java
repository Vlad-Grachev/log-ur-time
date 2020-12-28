package com.unn.logurtime.model.constants;

public interface Constants {
    interface Common {
        interface Parameters {
            String ID = "id";
            String NAME = "name";
        }
    }

    interface Role {
        int ADMIN_ROLE_ID = 1;
        int DIRECTOR_ROLE_ID = 2;
        int MANAGER_ROLE_ID = 2;
        int USER_ROLE_ID = 2;
    }

    interface Task {
        interface Parameters {
            String Description = "description";
            String CREATION_DATE = "creation_date";
            String DUE_DATE = "due_date";
            String ESTIMATION = "estimation";
            String CREATOR = "creator";
            String ASSIGNEE = "assignee";
            String STATUS = "status";
            String PRIORITY = "priority";
            String MOVE_DD_COUNTER = "move_dd_counter";
        }

    }

    interface TimeReport {
        interface Parameters {
            String USER_ID = "user_id";
            String TASK_ID = "task_id";
            String SPENT_TIME = "spent_time";
        }
    }
}
