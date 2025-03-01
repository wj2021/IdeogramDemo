CREATE DATABASE IF NOT EXISTS ideogram
CHARACTER SET utf8mb4
COLLATE utf8mb4_general_ci;

-- 图片表
CREATE TABLE IF NOT EXISTS ideo_pic (
    id VARCHAR(64) not null,
    user_id VARCHAR(64) not null,
    prompts text not null,
    pic_link VARCHAR(1024) not null,
    ori_pic_link VARCHAR(1024) not null,
    gmt_create datetime not null default current_timestamp,
    gmt_modified datetime not null default current_timestamp,
    unique index uk_id(id),
    index idx_user_id(user_id),
    key idx_user_id_gmt_create(user_id,gmt_create)
);
