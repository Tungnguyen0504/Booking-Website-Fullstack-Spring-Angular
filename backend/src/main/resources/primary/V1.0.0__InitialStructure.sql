/*
* Copyright: Tung Nguyen
* Position: Developer
* Email: tungnd.os@ncb-bank.vn
*/

CREATE TABLE IF NOT EXISTS APPLICATION
(
    ID                  UUID PRIMARY KEY DEFAULT GEN_RANDOM_UUID(), -- PRIMARY KEY UUID
    BPM_ID              VARCHAR(150) NOT NULL,                      -- Mã hồ sơ BPM
    BUSINESS_SCOPE_CODE VARCHAR(50)  NOT NULL,                      -- Mã nghiệp vụ
    WORKFLOW_CODE       VARCHAR(50)  NOT NULL,                      -- Mã quy trình
    CASE_STATUS         VARCHAR(50)  NOT NULL,                      -- Trạng thái hồ sơ
    APPROVAL_STATUS     VARCHAR(20),                                -- Trạng thái phê duyệt của hồ sơ
    APPROVAL_BY         VARCHAR(20),                                -- Người phê duyệt
    APPROVAL_TIME       TIMESTAMP,                                  -- Thời gian phê duyệt
    PROCESS_INSTANCE_ID UUID,                                       -- Mã process instance
    PRODUCT_CODE        VARCHAR(50),                                -- Mã sản phẩm/quy trình
    PRODUCT_DESCRIPTION VARCHAR(200),                               -- Tên/Mô tả sản phẩm/quy trình
    WORKFLOW_FLOW       VARCHAR(200),                               -- Các bước quy trình (luồng xử lý)
    FLOW_CODE           VARCHAR(100),                               -- Mã luồng xử lý
    UNIT                VARCHAR(200),                               -- Mã đơn vị
    UNIT_NAME           VARCHAR(200),                               -- Tên đơn vị
    WORKFLOW_ID         UUID,                                       -- ID quy trình
    STORAGE_REQUEST     VARCHAR(250),                               -- Mã kho đề xuất
    STORAGE_RECEIVER    VARCHAR(250),                               -- Mã kho tiếp nhận
    CREATED_BY          VARCHAR(50)  NOT NULL,                      -- User khởi tạo
    CREATED_TIME        TIMESTAMP    NOT NULL,                      -- Thời gian khởi tạo
    UPDATED_BY          VARCHAR(50),                                -- User cập nhật
    UPDATED_TIME        TIMESTAMP                                   -- Thời gian cập nhật
);

CREATE TABLE IF NOT EXISTS APP_CARD_REQUEST
(
    ID               UUID DEFAULT GEN_RANDOM_UUID() NOT NULL PRIMARY KEY, -- PRIMARY KEY UUID
    APPLICATION_ID   UUID                           NOT NULL,             -- ID của mã application
    KIOSK_STORAGE    VARCHAR(250),                                        -- mã kiosk
    CARD_RECIPIENT   VARCHAR(250),                                        -- Người Nhận Thẻ
    TOKEN_PRODUCT    VARCHAR(250),                                        -- loại thẻ
    CARDBLANK_STATUS VARCHAR(250),                                        -- loại phôi
    CARDBLANK_NUMBER INTEGER,                                             -- số lượng
    NOTIFY_TYPE      VARCHAR(50),                                         -- Loại thông báo
    CREATED_BY       VARCHAR(50)                    NOT NULL,             -- Người tạo
    CREATED_TIME     TIMESTAMP                      NOT NULL,             -- Thời gian tạo
    UPDATED_BY       VARCHAR(50),                                         -- Người cập nhật
    UPDATED_TIME     TIMESTAMP                                            -- Thời gian cập nhật
);

CREATE TABLE IF NOT EXISTS TX_CARD_ETL
(
    ID                       UUID DEFAULT GEN_RANDOM_UUID() NOT NULL PRIMARY KEY, -- PRIMARY KEY UUID
    TOKEN_ID                 VARCHAR(250) UNIQUE            NOT NULL,             -- Id của token ( card)
    CARD_NUMBER              VARCHAR(250),                                        -- Số thẻ
    TOKEN_PRODUCT            VARCHAR(250),                                        -- Hạng thẻ được phát hành
    CIF_NO                   VARCHAR(250),                                        -- Mã cif của khách hàng
    CUS_NAME                 VARCHAR(350),                                        -- Tên khách hàng
    PIN_CARD_DELIVERY_METHOD VARCHAR(250),                                        -- Phương thức nhận thẻ của KH
    CARD_CREATION_DATE       TIMESTAMP,                                           -- Thời gian khởi tạo bản ghi thẻ
    CARD_EMBOSSING_DATE      TIMESTAMP,                                           -- Ngày dập thẻ
    DAO_ISSUING              VARCHAR(250),                                        -- Dao phát hành thẻ
    DES_DAO_ISSUING          VARCHAR(250),                                        -- Tên Dao phát hành thẻ
    COM_ISSUING              VARCHAR(250),                                        -- COM phát hành thẻ
    DES_COM_ISSUING          VARCHAR(250),                                        -- Tên COM phát hành thẻ
    DAO_DELIVERY             VARCHAR(250),                                        -- DAO nhận thẻ
    DES_DAO_DELIVERY         VARCHAR(250),                                        -- Tên DAO nhận thẻ
    COM_DELIVERY             VARCHAR(250),                                        -- COM nhận thẻ
    DES_COM_DELIVERY         VARCHAR(250),                                        -- Tên COM nhận thẻ
    DELIVERY_ADDRESS         VARCHAR(250),                                        -- Địa chỉ nhận thẻ tại nhà riêng của KH
    PRINT_ADDRESS            VARCHAR(250),                                        -- Địa chỉ in thẻ.
    LIFEPHASE_STATUS         VARCHAR(250),                                        -- Trạng thái lifephase của Thẻ
    ID_TOKEN_PRODUCT         VARCHAR(250),                                        -- Mã ID sản phẩm thẻ cấp token
    CARD_BLANK_UPDATE        BOOLEAN,                                             -- Đã update vào danh sách của quản lý kho thẻ hay chưa
    CREATED_BY               VARCHAR(50)                    NOT NULL,             -- Người tạo
    CREATED_TIME             TIMESTAMP                      NOT NULL,             -- Thời gian tạo
    UPDATED_BY               VARCHAR(50),                                         -- Người cập nhật
    UPDATED_TIME             TIMESTAMP                                            -- Thời gian cập nhật
);

CREATE TABLE IF NOT EXISTS APP_CARD_DETAIL
(
    ID                 UUID DEFAULT GEN_RANDOM_UUID() NOT NULL PRIMARY KEY, -- PRIMARY KEY UUID
    APPLICATION_ID     UUID                           NOT NULL,             -- Id của bảng application
    TX_CARD_ETL_ID     UUID,                                                -- Id của bảng TX_CARD_ETL
    STORAGE_CODE       VARCHAR(250),                                        -- Mã Code kho
    BPM_CARD_STATUS    VARCHAR(250),                                        -- Trạng thái thẻ trên bpm
    DELIVERY_TX_STATUS BOOLEAN,                                             -- Trạng thái cập nhật trạng thái sang TX
    CREATED_BY         VARCHAR(50)                    NOT NULL,             -- Người tạo
    CREATED_TIME       TIMESTAMP                      NOT NULL,             -- Thời gian tạo
    UPDATED_BY         VARCHAR(50),                                         -- Người cập nhật
    UPDATED_TIME       TIMESTAMP                                            -- Thời gian cập nhật
);

CREATE TABLE IF NOT EXISTS STORAGE
(
    ID           UUID    DEFAULT GEN_RANDOM_UUID() NOT NULL PRIMARY KEY, -- PRIMARY KEY UUID
    STORAGE_CODE VARCHAR(100) UNIQUE               NOT NULL,             -- Mã code
    STORAGE_NAME VARCHAR(300)                      NOT NULL,             -- Tên kho
    MANAGER      VARCHAR(100),                                           -- Tên người quản lý
    ENABLED      BOOLEAN DEFAULT TRUE              NOT NULL,             -- Trạng thái
    TYPE         VARCHAR(20)                       NOT NULL,             -- Loại kho
    CREATED_BY   VARCHAR(50)                       NOT NULL,             -- Người tạo
    CREATED_TIME TIMESTAMP                         NOT NULL,             -- Thời gian tạo
    UPDATED_BY   VARCHAR(50),                                            -- Người cập nhật
    UPDATED_TIME TIMESTAMP                                               -- Thời gian cập nhật
);

CREATE TABLE IF NOT EXISTS STORAGE_HISTORY
(
    ID           UUID,            -- PRIMARY KEY UUID
    REV          BIGINT NOT NULL, -- ID của revision
    REVTYPE      SMALLINT,        -- Loại thay đổi
    STORAGE_CODE VARCHAR(100),    -- Mã code
    STORAGE_NAME VARCHAR(300),    -- Tên kho
    MANAGER      VARCHAR(100),    -- Tên người quản lý
    ENABLED      BOOLEAN,         -- Trạng thái
    TYPE         VARCHAR(20),     -- Loại kho
    CREATED_BY   VARCHAR(50),     -- Người tạo
    CREATED_TIME TIMESTAMP,       -- Thời gian tạo
    UPDATED_BY   VARCHAR(50),     -- Người cập nhật
    UPDATED_TIME TIMESTAMP        -- Thời gian cập nhật
);

CREATE TABLE IF NOT EXISTS PARENT_STORAGE
(
    ID                UUID    DEFAULT GEN_RANDOM_UUID() NOT NULL PRIMARY KEY, -- PRIMARY KEY UUID
    STORAGE_ID        UUID                              NOT NULL,             -- Storage ID
    PARENT_STORAGE_ID UUID                              NOT NULL,             -- Element ID
    ENABLED           BOOLEAN DEFAULT TRUE              NOT NULL,             -- Trạng thái
    CREATED_BY        VARCHAR(50)                       NOT NULL,             -- Người tạo
    CREATED_TIME      TIMESTAMP                         NOT NULL,             -- Thời gian tạo
    UPDATED_BY        VARCHAR(50),                                            -- Người cập nhật
    UPDATED_TIME      TIMESTAMP                                               -- Thời gian cập nhật
);

CREATE TABLE IF NOT EXISTS STORAGE_CARD
(
    ID               UUID DEFAULT GEN_RANDOM_UUID() NOT NULL PRIMARY KEY, -- PRIMARY KEY UUID
    STORAGE_CODE     VARCHAR(250)                   NOT NULL,             -- Code kho
    TOKEN_PRODUCT    VARCHAR(250)                   NOT NULL,             -- Loại phôi thẻ
    CARDBLANK_STATUS VARCHAR(250)                   NOT NULL,             -- Code trạng thái phôi
    CARDBLANK_NUMBER INTEGER,                                             -- Số lượng
    AMEND_NUMBER     INTEGER,                                             -- Số lượng thay đổi
    CARD_FAIL_NUMBER INTEGER,                                             -- Số lượng thẻ hỏng
    CREATED_BY       VARCHAR(50)                    NOT NULL,             -- Người tạo
    CREATED_TIME     TIMESTAMP                      NOT NULL,             -- Thời gian tạo
    UPDATED_BY       VARCHAR(50),                                         -- Người cập nhật
    UPDATED_TIME     TIMESTAMP                                            -- Thời gian cập nhật
);

CREATE TABLE IF NOT EXISTS STORAGE_CARD_HISTORY
(
    ID               UUID,
    REV              BIGINT,       -- ID của revision
    REVTYPE          SMALLINT,     -- Loại thay đổi
    STORAGE_CODE     VARCHAR(250), -- Code kho
    TOKEN_PRODUCT    VARCHAR(250), -- Loại phôi thẻ
    CARDBLANK_STATUS VARCHAR(250), -- Code trạng thái phôi
    CARDBLANK_NUMBER INTEGER,      -- Số lượng
    AMEND_NUMBER     INTEGER,      -- Số lượng thay đổi
    CARD_FAIL_NUMBER INTEGER,      -- Số lượng thẻ hỏng
    CREATED_BY       VARCHAR(50),  -- Người tạo
    CREATED_TIME     TIMESTAMP,    -- Thời gian tạo
    UPDATED_BY       VARCHAR(50),  -- Người cập nhật
    UPDATED_TIME     TIMESTAMP,    -- Thời gian cập nhật
    PRIMARY KEY (ID, REV)
);

CREATE TABLE IF NOT EXISTS STORAGE_CARD_INFO
(
    ID              UUID DEFAULT GEN_RANDOM_UUID() NOT NULL PRIMARY KEY, -- PRIMARY KEY UUID
    TX_CARD_ETL_ID  UUID,                                                -- Id của bảng TX_CARD_ETL
    STORAGE_CODE    VARCHAR(250),                                        -- Mã Code kho
    BPM_CARD_STATUS VARCHAR(250),                                        -- Trạng thái trên bpm
    BPM_ID_TRANS    VARCHAR(250),                                        -- Mã BPM chuyển thẻ về đơn vị
    BPM_ID_UPDATE   VARCHAR(250),                                        -- Mã bpm update thông tin
    RM_RECEIVE      VARCHAR(250),                                        -- RM nhận thẻ
    CPN_BILL        VARCHAR(250),                                        -- Mã bill CPN
    CREATED_BY      VARCHAR(50)                    NOT NULL,             -- Người tạo
    CREATED_TIME    TIMESTAMP                      NOT NULL,             -- Thời gian tạo
    UPDATED_BY      VARCHAR(50),                                         -- Người cập nhật
    UPDATED_TIME    TIMESTAMP                                            -- Thời gian cập nhật
);

CREATE TABLE IF NOT EXISTS STORAGE_CARD_INFO_HISTORY
(
    ID              UUID,         -- PRIMARY KEY UUID
    REV             BIGINT,       -- ID của revision
    REVTYPE         SMALLINT,     -- Loại thay đổi
    TX_CARD_ETL_ID  UUID,         -- Id của bảng TX_CARD_ETL
    STORAGE_CODE    VARCHAR(250), -- Mã code kho
    BPM_CARD_STATUS VARCHAR(250), -- Trạng thái trên bpm
    BPM_ID_TRANS    VARCHAR(250), -- Mã BPM chuyển thẻ về đơn vị
    BPM_ID_UPDATE   VARCHAR(250), -- Mã bpm update thông tin
    RM_RECEIVE      VARCHAR(250), -- RM nhận thẻ
    CPN_BILL        VARCHAR(250), -- Mã bill CPN
    CREATED_BY      VARCHAR(50),  -- Người tạo
    CREATED_TIME    TIMESTAMP,    -- Thời gian tạo
    UPDATED_BY      VARCHAR(50),  -- Người cập nhật
    UPDATED_TIME    TIMESTAMP,    -- Thời gian cập nhật
    PRIMARY KEY (ID, REV)
);

CREATE TABLE IF NOT EXISTS SEQUENCES
(
    ID           UUID      DEFAULT GEN_RANDOM_UUID() NOT NULL PRIMARY KEY,
    CODE         VARCHAR(50)                         NOT NULL,
    DESCRIPTION  VARCHAR(100)                        NOT NULL,
    SEQ          BIGINT    DEFAULT 1                 NOT NULL,
    UPDATED_BY   VARCHAR(50),
    UPDATED_TIME TIMESTAMP,
    CREATED_BY   VARCHAR(50)                         NOT NULL,
    CREATED_TIME TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tạo bảng TASKS
CREATE TABLE IF NOT EXISTS TASKS
(
    ID              UUID      DEFAULT GEN_RANDOM_UUID() NOT NULL PRIMARY KEY,
    BPM_ID          VARCHAR(100)                        NOT NULL,
    APPLICATION_ID  UUID                                NOT NULL,
    TASK_ID         UUID                                NOT NULL,
    ACTIVITY_ID     UUID,
    ACTIVITY_CODE   VARCHAR(50),
    ACTIVITY_NAME   VARCHAR(150),
    ACTIVITY_MODEL  VARCHAR(100),
    ACTIVITY_STEP   INTEGER,
    STAGE_ID        UUID,
    STAGE_CODE      VARCHAR(50),
    STAGE_NAME      VARCHAR(150),
    FROM_ACTIVITY   VARCHAR(50),
    ASSIGNEE        VARCHAR(20),
    ASSIGN_TO       VARCHAR(20),
    ASSIGN_MAIL     VARCHAR(150),
    ASSIGN_NAME     VARCHAR(255),
    ASSIGN_GROUP    VARCHAR(50),
    FORM_URL        VARCHAR(255),
    UNIT            VARCHAR(50),
    SLA             BOOLEAN   DEFAULT TRUE,
    REASON_CODE     VARCHAR(50),
    REASON_DESC     VARCHAR(500),
    COMMENTS        VARCHAR(1000),
    TASK_STATUS     VARCHAR(20)                         NOT NULL,
    APPROVAL_STATUS VARCHAR(20),
    ACTION_CODE     VARCHAR(50),
    REASSIGN_BY     VARCHAR(50),
    COMPLETED_TIME  TIMESTAMP,
    UPDATED_BY      VARCHAR(50),
    UPDATED_TIME    TIMESTAMP,
    CREATED_BY      VARCHAR(50)                         NOT NULL,
    CREATED_TIME    TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tạo bảng CC_EMAIL
CREATE TABLE IF NOT EXISTS CC_EMAIL
(
    ID             UUID        DEFAULT GEN_RANDOM_UUID() NOT NULL PRIMARY KEY,
    APPLICATION_ID UUID                                  NOT NULL,
    EMAIL          VARCHAR(50)                           NOT NULL,
    STATUS         VARCHAR(20) DEFAULT 'ACTIVE'          NOT NULL,
    UPDATED_BY     VARCHAR(50),
    UPDATED_TIME   TIMESTAMP,
    CREATED_BY     VARCHAR(50)                           NOT NULL,
    CREATED_TIME   TIMESTAMP   DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS COMMENT
(
    ID           UUID      DEFAULT GEN_RANDOM_UUID() NOT NULL PRIMARY KEY,
    REFERENCE_ID UUID,
    MESSAGE      TEXT,
    TYPE         VARCHAR(50),
    CREATED_BY   VARCHAR(50)                         NOT NULL,
    CREATED_TIME TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UPDATED_BY   VARCHAR(50),
    UPDATED_TIME TIMESTAMP
);

CREATE TABLE IF NOT EXISTS INTEGRATION_LOG
(
    ID             UUID      DEFAULT GEN_RANDOM_UUID() NOT NULL PRIMARY KEY,
    TRANSACTION_ID VARCHAR(50),
    REQUEST_TYPE   VARCHAR(100),
    DESCRIPTION    VARCHAR(255),
    HTTP_METHOD    VARCHAR(20)                         NOT NULL,
    ENDPOINT       VARCHAR(255)                        NOT NULL,
    RAW_REQUEST    JSONB                               NOT NULL,
    RAW_RESPONSE   JSONB,
    MESSAGE        TEXT,
    EXECUTION_TIME VARCHAR(20)                         NOT NULL,
    STATUS_CODE    INTEGER                             NOT NULL,
    STATE          VARCHAR(20)                         NOT NULL,
    CREATED_BY     VARCHAR(50)                         NOT NULL,
    CREATED_TIME   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UPDATED_BY     VARCHAR(50),
    UPDATED_TIME   TIMESTAMP
);

CREATE TABLE IF NOT EXISTS ECM_DOCUMENT
(
    ID                UUID PRIMARY KEY      DEFAULT GEN_RANDOM_UUID(),
    CREATED_BY        VARCHAR(50)  NOT NULL,
    CREATED_TIME      TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UPDATED_BY        VARCHAR(50),
    UPDATED_TIME      TIMESTAMP,
    DOC_CODE          VARCHAR(50)  NOT NULL,
    DOC_NAME          VARCHAR(100) NOT NULL,
    REFERENCE         VARCHAR(50)  NOT NULL,
    ECM_DOCUMENT_ID   VARCHAR(50)  NOT NULL,
    ECM_DOCUMENT_PATH VARCHAR(500),
    STATUS            VARCHAR(20)  NOT NULL
);

CREATE TABLE IF NOT EXISTS REVISION_INFO
(
    ID        BIGINT NOT NULL PRIMARY KEY,
    TIMESTAMP BIGINT NOT NULL
);

CREATE SEQUENCE revision_info_seq INCREMENT 1 START 1;

COMMENT ON TABLE REVISION_INFO IS 'Bảng lưu thông tin các revision trong hệ thống';
COMMENT ON COLUMN REVISION_INFO.ID IS 'ID của revision, tự động tăng';
COMMENT ON COLUMN REVISION_INFO.TIMESTAMP IS 'Thời điểm tạo revision (timestamp)';