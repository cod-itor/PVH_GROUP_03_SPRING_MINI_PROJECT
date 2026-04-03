CREATE DATABASE spring_project;

-- Create UUID extension (run this first)
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";


CREATE TABLE IF NOT EXISTS achievements
(
    achievement_id uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
    title          VARCHAR(50)  NOT NULL,
    description    VARCHAR(100) NOT NULL,
    badge          VARCHAR(100) NOT NULL,
    xp_required    INT          NOT NULL
);

CREATE TABLE IF NOT EXISTS app_users
(
    app_user_id uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
    username      VARCHAR(50)  UNIQUE NOT NULL,
    email         VARCHAR(50)  UNIQUE NOT NULL,
    password      VARCHAR(250)        NOT NULL,
    level         INT                 DEFAULT 0 NOT NULL,
    xp            INT                 DEFAULT 0 NOT NULL,
    profile_image VARCHAR(255),
    is_verified   BOOLEAN             DEFAULT FALSE NOT NULL,
    created_at    TIMESTAMP           DEFAULT CURRENT_TIMESTAMP NOT NULL
);


CREATE TABLE IF NOT EXISTS app_user_achievements
(
    app_user_id     uuid NOT NULL,
    achievement_id  uuid NOT NULL,
    achieved_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    PRIMARY KEY (app_user_id, achievement_id),
    CONSTRAINT fk_app_user FOREIGN KEY (app_user_id) REFERENCES app_users (app_user_id) ON DELETE CASCADE,
    CONSTRAINT fk_achievement FOREIGN KEY (achievement_id) REFERENCES achievements (achievement_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS habits
(
    habit_id    uuid PRIMARY KEY DEFAULT uuid_generate_v4(),
    title       varchar(50)  NOT NULL,
    description varchar(100) NOT NULL,
    frequency   varchar(25)  NOT NULL,
    is_active   BOOLEAN NOT NULL DEFAULT TRUE,
    app_user_id uuid NOT NULL,
    CONSTRAINT fk_app_user FOREIGN KEY (app_user_id) REFERENCES app_users (app_user_id) ON DELETE CASCADE,
    created_at  timestamp             DEFAULT CURRENT_TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS habit_logs
(
    habit_log_id uuid PRIMARY KEY DEFAULT uuid_generate_v4() ,
    log_date     TIMESTAMP   NOT NULL,
    status       varchar(25) NOT NULL,
    xp_earned    int         NOT NULL,
    habit_id     uuid REFERENCES habits (habit_id) ON DELETE CASCADE
);

INSERT INTO achievements (title, description, badge, xp_required)
VALUES
    ('First Habit Completed', 'Awarded when a user completes their first habit.', 'first_habit_badge.png', 50),
    ('7-Day Streak', 'Awarded for completing a habit for 7 consecutive days.', '7_day_streak_badge.png', 100),
    ('30-Day Streak', 'Awarded for completing a habit for 30 consecutive days.', '30_day_streak_badge.png', 200),
    ('Habit Master', 'Awarded for completing 10 different habits.', 'habit_master_badge.png', 500),
    ('Perfect Month', 'Awarded for completing a habit every day in a given month.', 'perfect_month_badge.png', 300),
    ('XP Novice', 'Awarded for earning your first 100 XP.', 'xp_novice_badge.png', 100),
    ('XP Champion', 'Awarded for earning 500 XP in total.', 'xp_champion_badge.png', 500),
    ('XP Overlord', 'Awarded for earning 5000 XP in total.', 'xp_overlord_badge.png', 5000),
    ('7-Day Streak Achievement', 'Awarded when a user completes a habit for 7 consecutive days.', '7_day_streak_achievement.png', 50),
    ('Level 10 Reached', 'Awarded when a user reaches level 10.', 'level_10_badge.png', 1000);