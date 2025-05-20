-- Thêm dữ liệu vào bảng User
INSERT INTO User (username, status_message, student_information, suid, phone_number, email, user_rank, avatar_url, password) VALUES
('nguyenvan_a', 'Đang ôn thi THPT Quốc Gia', 'Học sinh lớp 12A1, THPT Chu Văn An', 'SV12345', '0987654321', 'nguyenvana@gmail.com', 'Thành viên mới', 'https://example.com/avatars/nguyenvana.jpg', '$2a$10$abcdefghijklmnopqrstuvwxyz'),
('tranthib', 'Chuyên gia Toán học', 'Sinh viên năm 3, Đại học Bách Khoa Hà Nội', 'SV23456', '0912345678', 'tranthib@gmail.com', 'Chuyên gia', 'https://example.com/avatars/tranthib.jpg', '$2a$10$abcdefghijklmnopqrstuvwxyz'),
('lequoc_c', 'Yêu thích Vật lý', 'Sinh viên năm 2, Đại học Khoa học Tự nhiên', 'SV34567', '0923456789', 'lequocc@gmail.com', 'Người đóng góp', 'https://example.com/avatars/lequocc.jpg', '$2a$10$abcdefghijklmnopqrstuvwxyz'),
('phamthid', 'Đam mê Văn học', 'Học sinh lớp 11A3, THPT Nguyễn Du', 'SV45678', '0934567890', 'phamthid@gmail.com', 'Thành viên mới', 'https://example.com/avatars/phamthid.jpg', '$2a$10$abcdefghijklmnopqrstuvwxyz'),
('hoangvane', 'Thích giải bài tập Hóa học', 'Giáo viên Hóa học, THPT Lê Quý Đôn', 'GV56789', '0945678901', 'hoangvane@gmail.com', 'Giáo viên', 'https://example.com/avatars/hoangvane.jpg', '$2a$10$abcdefghijklmnopqrstuvwxyz');

-- Thêm dữ liệu vào bảng Topic (các môn học)
INSERT INTO Topic (name, description) VALUES
('Toán học', 'Các bài toán, lý thuyết và công thức Toán học từ cơ bản đến nâng cao'),
('Vật lý', 'Kiến thức về cơ học, điện từ học, quang học và vật lý hiện đại'),
('Hóa học', 'Lý thuyết và bài tập về hóa vô cơ, hóa hữu cơ và hóa phân tích'),
('Văn học', 'Phân tích tác phẩm, tác giả và các thể loại văn học Việt Nam và thế giới'),
('Tiếng Anh', 'Ngữ pháp, từ vựng và các kỹ năng nghe, nói, đọc, viết tiếng Anh'),
('Sinh học', 'Kiến thức về cơ thể người, động vật, thực vật và di truyền học'),
('Lịch sử', 'Các sự kiện và nhân vật lịch sử Việt Nam và thế giới'),
('Địa lý', 'Kiến thức về địa lý tự nhiên và địa lý kinh tế-xã hội Việt Nam và thế giới'),
('Tin học', 'Kiến thức về lập trình, thuật toán và ứng dụng công nghệ thông tin');

-- Thêm dữ liệu vào bảng Post
INSERT INTO Post (content, image, create_date, title, user_id) VALUES
('Làm thế nào để giải phương trình bậc hai ax^2 + bx + c = 0 khi a = 0? Tôi đang gặp khó khăn với bài tập này.', 'https://example.com/images/math_question1.jpg', '2025-05-10', 'Câu hỏi về phương trình bậc hai', 1),
('Ai có thể giải thích cho tôi hiện tượng quang điện và ứng dụng của nó trong đời sống? Tôi cần làm bài thuyết trình cho môn Vật lý.', 'https://example.com/images/physics_question1.jpg', '2025-05-11', 'Tìm hiểu về hiện tượng quang điện', 3),
('Làm thế nào để phân biệt ankan, anken và ankyn bằng phản ứng hóa học? Mình cần ví dụ cụ thể.', 'https://example.com/images/chemistry_question1.jpg', '2025-05-12', 'Phân biệt các loại hydrocarbon', 4),
('Phân tích hình tượng người lái đò trong tác phẩm "Người lái đò sông Đà" của Nguyễn Tuân. Cần gấp cho bài kiểm tra ngày mai!', 'https://example.com/images/literature_question1.jpg', '2025-05-13', 'Phân tích nhân vật trong tác phẩm của Nguyễn Tuân', 1),
('Cách sử dụng đúng thì hiện tại hoàn thành tiếp diễn trong tiếng Anh? Cho mình một số ví dụ với!', 'https://example.com/images/english_question1.jpg', '2025-05-14', 'Ngữ pháp tiếng Anh - Thì hiện tại hoàn thành tiếp diễn', 2),
('Ai có thể giải giúp mình bài toán khó này về giới hạn dãy số? Mình đã thử nhiều cách nhưng không ra kết quả.', 'https://example.com/images/math_question2.jpg', '2025-05-15', 'Bài toán giới hạn dãy số khó', 3),
('Tại sao nước sôi ở nhiệt độ thấp hơn trên đỉnh núi cao? Giải thích theo góc độ vật lý được không?', 'https://example.com/images/physics_question2.jpg', '2025-05-16', 'Câu hỏi về áp suất không khí và điểm sôi', 5),
('Cách xác định công thức phân tử của hợp chất hữu cơ bằng phương pháp đốt cháy?', 'https://example.com/images/chemistry_question2.jpg', '2025-05-17', 'Phương pháp xác định công thức phân tử', 2);

-- Thêm dữ liệu vào bảng post_images (ảnh bổ sung cho post)
INSERT INTO post_images (post_id, image_url) VALUES
(1, 'https://example.com/images/math_question1_add1.jpg'),
(1, 'https://example.com/images/math_question1_add2.jpg'),
(2, 'https://example.com/images/physics_question1_add1.jpg'),
(3, 'https://example.com/images/chemistry_question1_add1.jpg'),
(3, 'https://example.com/images/chemistry_question1_add2.jpg'),
(6, 'https://example.com/images/math_question2_add1.jpg');

-- Thêm mối quan hệ giữa Post và Topic
INSERT INTO post_topic (post_id, topic_id) VALUES
(1, 1), -- Post 1 (phương trình bậc hai) thuộc Topic 1 (Toán học)
(2, 2), -- Post 2 (hiện tượng quang điện) thuộc Topic 2 (Vật lý)
(3, 3), -- Post 3 (hydrocarbon) thuộc Topic 3 (Hóa học)
(4, 4), -- Post 4 (phân tích văn học) thuộc Topic 4 (Văn học)
(5, 5), -- Post 5 (ngữ pháp tiếng Anh) thuộc Topic 5 (Tiếng Anh)
(6, 1), -- Post 6 (giới hạn dãy số) thuộc Topic 1 (Toán học)
(7, 2), -- Post 7 (áp suất và điểm sôi) thuộc Topic 2 (Vật lý)
(8, 3); -- Post 8 (công thức phân tử) thuộc Topic 3 (Hóa học)

-- Thêm dữ liệu vào bảng Comment
INSERT INTO Comment (content, create_date, user_id, post_id, parent_comment_id) VALUES
('Khi a = 0, phương trình trở thành bx + c = 0, đây là phương trình bậc 1. Nếu b ≠ 0, nghiệm là x = -c/b. Nếu b = 0 và c = 0, phương trình có vô số nghiệm. Nếu b = 0 và c ≠ 0, phương trình vô nghiệm.', '2025-05-10', 2, 1, NULL),
('Cảm ơn bạn nhiều! Bây giờ mình đã hiểu rõ hơn rồi.', '2025-05-10', 1, 1, 1),
('Hiện tượng quang điện là hiện tượng electron bị bứt ra khỏi bề mặt kim loại khi chiếu ánh sáng có bước sóng thích hợp. Ứng dụng: pin mặt trời, cảm biến ánh sáng, máy đo ánh sáng,...', '2025-05-11', 5, 2, NULL),
('Bạn có thể giải thích thêm về lý thuyết lượng tử liên quan đến hiện tượng này không?', '2025-05-11', 3, 2, 3),
('Theo lý thuyết lượng tử, ánh sáng gồm các hạt photon, mỗi photon mang năng lượng E = hν (h: hằng số Planck, ν: tần số ánh sáng). Khi photon có đủ năng lượng (lớn hơn năng lượng liên kết của electron), nó có thể bứt electron ra khỏi bề mặt kim loại.', '2025-05-12', 5, 2, NULL),
('Để phân biệt: Ankan không làm mất màu dung dịch brom và KMnO4. Anken làm mất màu cả hai. Ankyn làm mất màu và tạo kết tủa với dung dịch AgNO3/NH3 (phản ứng tạo Ag2C2).', '2025-05-12', 5, 3, NULL),
('Ví dụ cụ thể: CH3-CH3 (etan, ankan) không phản ứng với Br2. CH2=CH2 (eten, anken) làm mất màu Br2 tạo CH2Br-CH2Br. HC≡CH (axetylen, ankyn) tạo kết tủa đỏ nâu với AgNO3/NH3.', '2025-05-12', 3, 3, 6),
('Trong "Người lái đò sông Đà", hình tượng người lái đò là biểu tượng cho con người chiến thắng thiên nhiên bằng trí tuệ và bản lĩnh. Nguyễn Tuân đã khắc họa nhân vật với sự dũng cảm, mưu trí và kinh nghiệm dày dạn.', '2025-05-13', 4, 4, NULL),
('Ngoài ra, người lái đò còn là hiện thân của vẻ đẹp lao động và tình yêu với nghề nghiệp. Văn phong miêu tả rất độc đáo, đậm chất tài hoa của Nguyễn Tuân.', '2025-05-13', 2, 4, 8),
('Thì hiện tại hoàn thành tiếp diễn (Present Perfect Continuous) dùng để diễn tả hành động bắt đầu trong quá khứ, kéo dài đến hiện tại và có thể tiếp tục. Cấu trúc: S + have/has + been + V-ing', '2025-05-14', 3, 5, NULL);

-- Thêm dữ liệu vào bảng comment_images (ảnh cho comment)
INSERT INTO comment_images (comment_id, image_url) VALUES
(1, 'https://example.com/images/math_solution1.jpg'),
(3, 'https://example.com/images/physics_explanation1.jpg'),
(5, 'https://example.com/images/physics_explanation2.jpg'),
(6, 'https://example.com/images/chemistry_explanation1.jpg'),
(7, 'https://example.com/images/chemistry_example1.jpg');

-- Thêm dữ liệu vào bảng React
INSERT INTO React (type, create_date, user_id, post_id) VALUES
('like', '2025-05-10', 2, 1),
('like', '2025-05-10', 3, 1),
('like', '2025-05-11', 1, 2),
('like', '2025-05-11', 4, 2),
('like', '2025-05-11', 5, 2),
('like', '2025-05-12', 1, 3),
('like', '2025-05-13', 2, 4),
('like', '2025-05-13', 3, 4),
('like', '2025-05-13', 5, 4),
('like', '2025-05-14', 1, 5),
('like', '2025-05-15', 2, 6),
('like', '2025-05-16', 3, 7),
('like', '2025-05-10', 4, 1),
('like', '2025-05-12', 2, 3),
('helpful', '2025-05-13', 1, 4),
('helpful', '2025-05-14', 4, 5),
('helpful', '2025-05-15', 5, 6);

-- Thêm Comment bổ sung và Reply Comment (chỉ 1 lớp reply)
INSERT INTO Comment (content, create_date, user_id, post_id, parent_comment_id) VALUES
('Ví dụ: "I have been studying for 3 hours" (Tôi đã học được 3 tiếng và có thể vẫn đang học). "She has been working here since 2020" (Cô ấy đã làm việc ở đây từ năm 2020 đến nay).', '2025-05-14', 5, 5, 10),
('Vậy thì này khác với thì hiện tại hoàn thành thường (Present Perfect) ở chỗ nào vậy?', '2025-05-14', 1, 5, 10),
('Present Perfect (đã hoàn thành) nhấn mạnh KẾT QUẢ của hành động, còn Present Perfect Continuous nhấn mạnh QUÁ TRÌNH diễn ra hành động. So sánh: "I have read 5 books" (kết quả) vs "I have been reading books all day" (quá trình).', '2025-05-15', 3, 5, NULL),
('Giới hạn của dãy số phụ thuộc vào cách tiếp cận. Đầu tiên, hãy xác định quy luật của dãy số, sau đó tính lim(n→∞). Nếu bạn cung cấp dãy số cụ thể, tôi có thể giúp giải chi tiết hơn.', '2025-05-15', 2, 6, NULL),
('Dãy số là: a_n = (n²+1)/(n+1). Mình cần tính lim(n→∞) a_n.', '2025-05-15', 3, 6, 14),
('Để tính lim(n→∞) (n²+1)/(n+1), ta chia cả tử số và mẫu số cho n (số có bậc cao nhất). Ta được: lim(n→∞) (n+1/n)/(1+1/n) = lim(n→∞) (1+1/n²)/(1/n+1/n²). Khi n→∞, 1/n²→0 và 1/n→0, nên ta có lim(n→∞) 1/0 = n. Vậy giới hạn của dãy số là n.', '2025-05-15', 5, 6, NULL),
('Bạn đang nhầm lẫn. Đúng phải là: lim(n→∞) (n²+1)/(n+1) = lim(n→∞) [n²(1+1/n²)]/[n(1+1/n)] = lim(n→∞) [n(1+1/n²)]/(1+1/n). Khi n→∞, 1/n²→0 và 1/n→0, nên ta có lim(n→∞) [n(1+0)]/(1+0) = lim(n→∞) n/1 = lim(n→∞) n = ∞. Vậy giới hạn của dãy số là vô cùng.', '2025-05-16', 2, 6, 16),
('Cảm ơn bạn nhiều! Mình đã hiểu cách giải rồi.', '2025-05-16', 3, 6, 16),
('Nước sôi ở nhiệt độ thấp hơn trên đỉnh núi cao vì áp suất không khí giảm theo độ cao. Áp suất thấp làm giảm điểm sôi của chất lỏng. Ở mực nước biển (áp suất 1 atm), nước sôi ở 100°C, nhưng trên đỉnh Everest, nước sôi ở khoảng 70°C.', '2025-05-16', 2, 7, NULL),
('Đây là ứng dụng của định luật Clausius-Clapeyron trong nhiệt động lực học, liên quan đến mối quan hệ giữa áp suất hơi và nhiệt độ. Công thức: ln(P₂/P₁) = (ΔH_vap/R)*(1/T₁ - 1/T₂), trong đó P là áp suất, T là nhiệt độ, ΔH_vap là enthalpy bay hơi, và R là hằng số khí lý tưởng.', '2025-05-16', 3, 7, 19);